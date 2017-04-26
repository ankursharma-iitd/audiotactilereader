Imports System.Math

' Different set of classes used for creation of DCEL

Public Class half_edge
    Public prev As half_edge
    Public nex As half_edge
    Public twin As half_edge
    Public tail As vertex
    Public left As face
End Class

Public Structure location
    Public x As Integer
    Public y As Integer
End Structure

Public Class vertex
    Public ver As location
    Public rep As half_edge
End Class

Public Class face
    Public rep As half_edge
End Class

' Main class for working with the DCEL

Public Class Reader
    ' Global list of faces, edges and vertices.
    Public facelist(-1) As face
    Public edgelist(-1) As half_edge
    Public vertexlist(-1) As vertex

    ' Points closer than the 'threrr' are merged together. Smaller the value of threrr, more accurate conversions are made
    ' but number of points to be stored in our data structure are also increased.
    Dim threrr As Integer = 50


    ' Add a new vertex to a path, given the previous vertex and half-edge, and then returns this vertex
    Public Function addvertex(ByVal x As Integer, ByVal y As Integer, ByVal p As vertex, ByVal pe As half_edge) As vertex
        Dim temp As location = New location
        temp.x = x
        temp.y = y
        Dim ans As vertex = New vertex
        ans.ver = temp
        Dim front As half_edge = New half_edge
        Dim back As half_edge = New half_edge
        p.rep = front
        front.twin = back
        back.twin = front
        pe.nex = front
        front.prev = pe
        back.nex = pe.twin
        pe.twin.prev = back
        front.tail = p
        back.tail = ans
        ReDim Preserve edgelist(UBound(edgelist) + 1)       ' Add the new half-edge formed in the edge list
        edgelist(UBound(edgelist)) = front
        ReDim Preserve edgelist(UBound(edgelist) + 1)       ' Add the new half-edge formed in the edge list
        edgelist(UBound(edgelist)) = back
        Return ans
    End Function

    ' Main function for conversion from array of points to DCEL.
    ' points is an 2-d array which contains x and y for each coordinate in seperate cell
    ' leng is the length of each row (obviously always divisible by 2)
    ' isclosed is a boolean array to represent whether the figure is closed is closed or not (this way the first point is not repeated in a
    '       closed figure instead isclosed value of the row becomes true)
    ' numl is the length of the points array (number of rows)
    Public Function draw(ByVal points As Integer(,), ByVal leng As Integer(), ByVal isclosed As Boolean(), ByVal numl As Integer) As vertex()
        Dim ans(-1) As vertex
        For i As Integer = 0 To numl - 1
            Dim start As vertex
            Dim temp1 As location = New location
            temp1.x = points(i, 0)
            temp1.y = points(i, 1)
            Dim ver1 As vertex = New vertex
            ver1.ver = temp1
            Dim temp2 As location = New location
            temp2.x = points(i, 2)
            temp2.y = points(i, 3)
            Dim ver2 As vertex = New vertex
            ver2.ver = temp2
            Dim front As half_edge = New half_edge
            Dim back As half_edge = New half_edge
            ver1.rep = front
            front.twin = back
            back.twin = front
            front.tail = ver1
            back.tail = ver2
            ReDim Preserve ans(UBound(ans) + 1)
            ans(UBound(ans)) = ver1
            ReDim Preserve ans(UBound(ans) + 1)
            ans(UBound(ans)) = ver2
            ReDim Preserve edgelist(UBound(edgelist) + 1)
            edgelist(UBound(edgelist)) = front
            ReDim Preserve edgelist(UBound(edgelist) + 1)
            edgelist(UBound(edgelist)) = back
            start = ver1
            Dim k As Integer = 4
            While k < leng(i)
                Dim tempv As vertex
                tempv = addvertex(points(i, k), points(i, k + 1), ver2, ver1.rep)
                ver1 = ver2
                ver2 = tempv
                ReDim Preserve ans(UBound(ans) + 1)
                ans(UBound(ans)) = ver2
                k = k + 2
            End While

            ' If the figure is not closed than no more data structures are required to be added. But if it is a closed figure, two more
            ' half-edges needs to be added.
            If isclosed(i) = False Then
                ver2.rep = ver1.rep.twin
            Else
                Dim fronta As half_edge = New half_edge
                Dim backa As half_edge = New half_edge
                ver2.rep = fronta
                fronta.twin = backa
                backa.twin = fronta
                fronta.tail = ver2
                backa.tail = start
                fronta.nex = start.rep
                start.rep.prev = fronta
                fronta.prev = ver1.rep
                ver1.rep.nex = fronta
                backa.prev = start.rep.twin
                start.rep.twin.nex = backa
                backa.nex = ver1.rep.twin
                ver1.rep.twin.prev = backa
                ReDim Preserve edgelist(UBound(edgelist) + 1)
                edgelist(UBound(edgelist)) = fronta
                ReDim Preserve edgelist(UBound(edgelist) + 1)
                edgelist(UBound(edgelist)) = backa
            End If
        Next
        Return ans
    End Function


    ' Checks whether two vertices should be merged or not given the value of 'threrr' globally declared.
    Public Function match(ByVal v1 As vertex, ByVal v2 As vertex) As Boolean
        Dim xerr As Integer
        Dim yerr As Integer
        If v1.ver.x < v2.ver.x Then
            xerr = v2.ver.x - v1.ver.x
        Else
            xerr = v1.ver.x - v2.ver.x
        End If
        If v1.ver.y < v2.ver.y Then
            yerr = v2.ver.y - v1.ver.y
        Else
            yerr = v1.ver.y - v2.ver.y
        End If
        If xerr <= threrr And yerr <= threrr Then
            Return True
        Else Return False
        End If
    End Function


    Public Function ismember(ByVal mem As vertex, ByVal lst As vertex()) As Boolean
        Dim i As Integer = 0
        While i < lst.Length
            If match(mem, lst(i)) = True Then
                Return True
            End If
            i = i + 1
        End While
        Return False
    End Function


    Public Function member(ByVal mem As vertex, ByVal lst As vertex()) As vertex
        Dim i As Integer = 0
        While i < lst.Length
            If match(mem, lst(i)) = True Then
                Return lst(i)
            End If
            i = i + 1
        End While
    End Function


    Public Function alledgesfromv(ByVal v As vertex) As half_edge()
        Dim leng As Integer = 0
        Dim start As half_edge
        start = v.rep
        Dim curr As half_edge
        curr = start.twin.nex
        While curr IsNot start And curr IsNot Nothing
            leng = leng + 1
            curr = curr.twin.nex
        End While
        Dim ans(leng) As half_edge
        leng = 0
        ans(leng) = start
        leng = leng + 1
        curr = start.twin.nex
        While curr IsNot start And curr IsNot Nothing
            ans(leng) = curr
            leng = leng + 1
            curr = curr.twin.nex
        End While
        Return ans
    End Function


    Public Function angles(ByVal a As vertex, ByVal b As vertex) As half_edge
        Dim one As location
        one.x = a.ver.x
        one.y = a.ver.y
        Dim two As location
        two.x = b.ver.x
        two.y = b.ver.y
        Dim arr() As half_edge
        arr = alledgesfromv(b)
        Dim slopes(arr.Length) As Double
        For i As Integer = 0 To arr.Length - 1
            Dim hey As half_edge = arr(i)
            Dim yayy As vertex
            yayy = hey.twin.tail
            Dim ofyayy As location
            ofyayy.x = yayy.ver.x
            ofyayy.y = yayy.ver.y
            slopes(i) = (ofyayy.y - two.y) / (ofyayy.x - two.x)
        Next
        Dim mainsl As Double = (one.y - two.y) / (one.x - two.x)
        For i As Integer = 0 To arr.Length - 1
            slopes(i) = (mainsl - slopes(i)) / (1 + mainsl * slopes(i))
        Next
        Dim min As Double = slopes(0)
        Dim Val As Integer
        For i As Integer = 0 To arr.Length - 1
            If min > slopes(i) And slopes(i) > 0 Then
                min = slopes(i)
                Val = i
            End If
        Next
        Dim toret As half_edge = arr(Val)
        Return toret
    End Function

    ' Add a new half-edge to a vertex and insert it at the correct location by calculating the angles. Read about DCEL to understand better.
    Public Function addedge(ByVal b As half_edge, ByVal t As half_edge, ByVal a As vertex)
        Dim temp As half_edge
        temp = t.twin.nex
        t.twin.nex = b
        b.prev = t.twin
        If temp Is Nothing Then
            b.twin.nex = t
            t.prev = b.twin
        Else
            b.twin.nex = temp
            temp.prev = b.twin
        End If
        b.tail = a
        Return 0
    End Function

    ' Merge two points by adding all the half-edges of one vertex to the other vertex
    Public Function merge(ByVal a As vertex, ByVal b As vertex)
        Dim arr() As half_edge = alledgesfromv(a)
        Dim tempo As half_edge
        For i As Integer = 0 To arr.Length - 1
            tempo = angles(arr(i).twin.tail, b)
            addedge(arr(i), tempo, b)
        Next
        Return 0
    End Function

    ' Traverse through the vertex list and merge all those vertices which needs to be merged
    Public Function sort(ByVal lst As vertex())
        For i As Integer = 0 To lst.Length - 1
            If ismember(lst(i), vertexlist) = True Then
                merge(lst(i), member(lst(i), vertexlist))
            Else
                ReDim Preserve vertexlist(UBound(vertexlist) + 1)
                vertexlist(UBound(vertexlist)) = lst(i)
            End If
        Next
        Return 0
    End Function

    Public Function traversal(ByVal he As half_edge) As face
        Dim flag As Boolean = False
        Dim ans As face = New face
        Dim start As half_edge
        start = he
        Dim curr As half_edge
        curr = he.nex
        If curr IsNot Nothing Then
            While curr IsNot start
                curr.left = ans
                curr = curr.nex
                If curr Is Nothing Then
                    flag = True
                    Exit While
                End If
            End While
        Else
            flag = True
        End If
        If flag = True Then
            curr = start
            While curr IsNot Nothing
                curr.left = ans
                curr = curr.prev
            End While
        End If
        ans.rep = start
        Return ans
    End Function

    ' Note: Not every face created in this function is a closed face, some edges which are not closed figures have been assigned
    ' hypothetical faces to avoid analysing them twice. However while checking point inside a face, such faces are ignored.  
    Public Function createface(ByVal arr As half_edge())
        ReDim facelist(arr.Length - 1)
        Dim leng As Integer = 0
        For i As Integer = 0 To arr.Length - 1
            If arr(i).left Is Nothing Then
                facelist(leng) = traversal(arr(i))
                leng = leng + 1
            End If
        Next
        ReDim Preserve facelist(leng - 1)
        Return 0
    End Function

    Public Function start(ByVal points As Integer(,), ByVal leng As Integer(), ByVal isclosed As Boolean(), ByVal numl As Integer)
        Dim tempver() As vertex
        tempver = draw(points, leng, isclosed, numl)        ' Start by converting the array of points into an array of vertices and half-edges
        sort(tempver)                                       ' Merge the vertices that match and create a final list of vertices
        createface(edgelist)                                ' Traverse through the edgelist to create faces
        Return 0
    End Function

    Public Function tan_inv(ByVal m As Double) As Integer
        Dim a As Double = Atan(m)
        Return a * 180 / Math.PI
    End Function

    Public Function ang_cw(ByVal a As vertex, ByVal b As vertex, ByVal p As location) As Integer
        Dim m1 As Double = (a.ver.y - p.y) / (a.ver.x - p.x)
        Dim m2 As Double = (b.ver.y - p.y) / (b.ver.x - p.x)
        Dim theta1 As Integer = tan_inv(m1)
        Dim theta2 As Integer = tan_inv(m2)
        Dim ans1 As Integer
        Dim ans2 As Integer
        If theta1 < 0 Then
            If (a.ver.y - p.y) >= 0 Then
                ans1 = 180 + theta1
            Else
                ans1 = 360 + theta1
            End If
        Else
            If (a.ver.y - p.y) >= 0 Then
                ans1 = theta1
            Else
                ans1 = 180 + theta1
            End If
        End If
        If theta2 < 0 Then
            If (b.ver.y - p.y) >= 0 Then
                ans2 = 180 + theta2
            Else
                ans2 = 360 + theta2
            End If
        Else
            If (b.ver.y - p.y) >= 0 Then
                ans2 = theta2
            Else
                ans2 = 180 + theta2
            End If
        End If
        Dim ans As Integer = ans1 - ans2
        Return ans
    End Function

    ' 'p' is the location of the point and 'fact' is the scaling factor used to display the whole image in the 'fit-screen' view
    Public Function pointin_p(ByVal p As location, ByVal fact As Double) As face
        Dim poi As location = New location
        poi.x = p.x * fact
        poi.y = p.y * fact
        For i As Integer = 0 To facelist.Length - 1
            Dim per As half_edge = facelist(i).rep
            Dim sta As half_edge = facelist(i).rep
            Dim flag As Integer = 0
            While True
                If per.nex Is Nothing Then
                    Exit While
                Else
                    If per.nex Is sta Then
                        Dim valt As Double = ang_cw(per.tail, per.nex.tail, poi)
                        If ((valt > 0 And valt < 180) Or (valt < -180 And valt > -360)) Then
                            flag = 1
                            Exit While
                        Else
                            Exit While
                        End If
                    End If
                    Dim val As Double = ang_cw(per.tail, per.nex.tail, poi)
                    If ((val >= 0 And val <= 180) Or (val <= -180 And val >= -360)) Then
                        per = per.nex
                    Else
                        Exit While
                    End If
                End If
            End While
            If flag = 1 Then
                Return facelist(i)
            End If
        Next
        Return Nothing
    End Function

    ' Add a new manually created polygon (no open figure) to the DCEL
    Public Function addface(ByVal arr() As location)
        Dim ans(-1) As vertex
        Dim start As vertex
        Dim temp1 As location = New location
        temp1.x = arr(0).x
        temp1.y = arr(0).y
        Dim ver1 As vertex = New vertex
        ver1.ver = temp1
        Dim temp2 As location = New location
        temp2.x = arr(1).x
        temp2.y = arr(1).y
        Dim ver2 As vertex = New vertex
        ver2.ver = temp2
        Dim front As half_edge = New half_edge
        Dim back As half_edge = New half_edge
        ver1.rep = front
        front.twin = back
        back.twin = front
        front.tail = ver1
        back.tail = ver2
        ReDim Preserve ans(UBound(ans) + 1)
        ans(UBound(ans)) = ver1
        ReDim Preserve ans(UBound(ans) + 1)
        ans(UBound(ans)) = ver2
        ReDim Preserve edgelist(UBound(edgelist) + 1)
        edgelist(UBound(edgelist)) = front
        ReDim Preserve edgelist(UBound(edgelist) + 1)
        edgelist(UBound(edgelist)) = back
        start = ver1
        For k As Integer = 2 To arr.Length - 1
            Dim tempv As vertex
            tempv = addvertex(arr(k).x, arr(k).y, ver2, ver1.rep)
            ver1 = ver2
            ver2 = tempv
            ReDim Preserve ans(UBound(ans) + 1)
            ans(UBound(ans)) = ver2
        Next


        Dim fronta As half_edge = New half_edge
        Dim backa As half_edge = New half_edge
        ver2.rep = fronta
        fronta.twin = backa
        backa.twin = fronta
        fronta.tail = ver2
        backa.tail = start
        fronta.nex = start.rep
        start.rep.prev = fronta
        fronta.prev = ver1.rep
        ver1.rep.nex = fronta
        backa.prev = start.rep.twin
        start.rep.twin.nex = backa
        backa.nex = ver1.rep.twin
        ver1.rep.twin.prev = backa
        ReDim Preserve edgelist(UBound(edgelist) + 1)
        edgelist(UBound(edgelist)) = fronta
        ReDim Preserve edgelist(UBound(edgelist) + 1)
        edgelist(UBound(edgelist)) = backa

        sort(ans)
        createface(edgelist)

        Return 0
    End Function
End Class
