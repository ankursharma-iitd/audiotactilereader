Imports cannystillcop.Reader﻿


Public Class Converter

    ' Global declarations so that the updated values can be used outside this code after the file is read
    Public wid As Integer = 1360
    Public hei As Integer = 640
    Dim MAXPATH As Integer = 0
    Dim MAXSIZE As Integer = 10000
    Public points(,) As Integer
    Public pathLength() As Integer
    Public isClosed() As Boolean
    Public numPaths As Integer = 0


    Public Function GetAngle(ByVal x As Double, ByVal y As Double) As Double
        Dim angle As Double
        If x = 0 Then
            angle = Math.PI / 2
        Else
            angle = Math.Atan(y / x)
        End If
        angle = angle * (180 / Math.PI)
        If x < 0 And y > 0 Then
            angle = angle + 180
        ElseIf x < 0 And y < 0 Then
            angle = 180 + angle
        ElseIf x > 0 And y < 0 Then
            angle = 360 + angle
        ElseIf x = 0 And y < 0 Then
            angle = 270
        ElseIf y = 0 And x < 0 Then
            angle = 180
        End If
        If x = 0 And y = 0 Then
            MsgBox("Angle Error")
        End If
        GetAngle = angle
    End Function

    Public Function GetInt(ByRef str As String, ByRef j As Integer) As Integer
        Dim val As Integer = 0
        Dim zero As Integer = Convert.ToInt32(("0"c))
        Dim sign As Integer = 1
        If str.Chars(j) = "-" Then
            sign = -1
            j = j + 1
        End If
        While str.Chars(j) <= "9" And str.Chars(j) >= "0"
            val = Convert.ToInt32(str.Chars(j)) - zero + val * 10
            j += 1
        End While
        GetInt = sign * val
    End Function


    ' Given a location, an array of faces and an array of file names, it creates two .txt files in the location specified
    ' named "ploygons.txt" & "sounds.txt" to map every polygon with an audio file
    Public Function WriteData(ByRef start() As face, ByRef files() As String, ByRef str As String)
        My.Computer.FileSystem.WriteAllText(str & "\polygons.txt", vbNewLine + start.Length.ToString() & vbNewLine, False)
        My.Computer.FileSystem.WriteAllText(str + "\sounds.txt", vbNewLine, False)
        For i As Integer = 0 To start.Length - 1
            Dim head As half_edge = start(i).rep
            Dim temp As half_edge = head
            Dim polygonstring As String = ""
            Dim abspath As String = files(i)
            Dim j As Integer = abspath.Length - 1
            While abspath.Chars(j) <> "\"c
                j -= 1
            End While
            My.Computer.FileSystem.WriteAllText(str + "\sounds.txt", (i + 1).ToString() + " " + abspath.Substring(j + 1, abspath.Length() - j - 5) + vbNewLine, True)
            Dim numpolygon As Integer = 0
            While True
                polygonstring += (temp.tail.ver.x.ToString() + " " + temp.tail.ver.y.ToString() + " ")
                numpolygon += 1
                temp = temp.nex
                If temp Is head Then
                    Exit While
                End If
            End While
            My.Computer.FileSystem.WriteAllText(str + "\polygons.txt", numpolygon.ToString() + " " + polygonstring + vbNewLine, True)
        Next
        Return 0
    End Function


    ' Takes a text file address as input and gives and output audio file at the location specified 
    Public Function texttospeech(ByVal inl As String, ByVal outl As String)
        Dim filestr As String = My.Computer.FileSystem.ReadAllText(inl)
        Dim fs = CreateObject("SAPI.spFileStream")
        Dim voice = CreateObject("SAPI.spVoice")
        fs.Open(inl, 3, False)
        voice.AudioOutputStream = fs
        voice.Speak(filestr)
        Return 0
    End Function



    ' The main function which reads all the tags in the SVG file and converts them into array of points.
    Public Function Main(ByVal s As String)
        Dim x1 As Integer
        Dim x2 As Integer
        Dim y1 As Integer
        Dim y2 As Integer
        Dim filestr As String
        filestr = My.Computer.FileSystem.ReadAllText(s)
        filestr = filestr.Replace(",", " ")
        filestr = filestr.Replace("polygon", "path").Replace("points", "d")
        Dim index As Integer = 0
        index = filestr.IndexOf("viewBox", index)
        index = index + 9
        x1 = GetInt(filestr, index)
        index += 1
        y1 = GetInt(filestr, index)
        index += 1
        x2 = GetInt(filestr, index)
        index += 1
        y2 = GetInt(filestr, index)

        wid = x2 - x1
        hei = y2 - y1

        While True
            index = filestr.IndexOf("<", index)
            If index < 0 Then
                Exit While
            End If
            If filestr.Substring(index + 1, 4) = "path" Or filestr.Substring(index + 1, 7) = "polygon" Then
                MAXPATH += 1
            End If
            index += 1
        End While


        MAXPATH = 5 * MAXPATH
        Dim str As String = "path"
        Dim start_index As Integer = 0
        Dim x As Integer = 0
        Dim y As Integer = 0
        ReDim points(MAXPATH, MAXSIZE)
        ReDim pathLength(MAXPATH)
        ReDim isClosed(MAXPATH)
        For i As Integer = 0 To MAXPATH
            isClosed(i) = True
        Next
        Dim endIndex As Integer = 0
        start_index = filestr.IndexOf(str, start_index)
        Dim doloop As Boolean = False
        If start_index > 0 Then
            doloop = True
        End If
        numPaths = 0

        
        While doloop
            Dim a_index As Integer = 0
            Dim flag As Boolean = False
            Dim cflag As Integer = 0
            Dim rflag As Boolean = False
            Dim cmd As Char = "M"
            Dim sign As Integer = 1
            Dim firstX As Integer = 0
            Dim firstY As Integer = 0
            Dim continueFlag As Boolean = False
            Dim firstFlag As Integer = 0
            start_index = filestr.IndexOf("d", start_index)
            start_index = filestr.IndexOf("""", start_index)
            endIndex = filestr.IndexOf("""", start_index + 1)
            For i As Integer = start_index + 1 To endIndex - 1
                Select Case filestr.Chars(i)
                    Case "M"
                        rflag = False
                        cmd = "M"
                    Case "m"
                        rflag = True
                        cmd = "m"
                    Case "l"
                        rflag = True
                        cmd = "l"
                    Case "c"
                        rflag = True
                        cflag = 1
                        cmd = "c"
                    Case ","
                    Case "z"
                        If endIndex - i > 3 Then
                            Dim tempi As Integer = i + 2
                            Dim nextx As Integer = GetInt(filestr, tempi)
                            tempi = tempi + 1
                            Dim nexty As Integer = GetInt(filestr, tempi)
                            Dim prevx As Integer = points(numPaths, a_index - 2)
                            Dim prevy As Integer = points(numPaths, a_index - 1)

                            Dim firstvx As Integer = points(numPaths, 0)
                            Dim firstvy As Integer = points(numPaths, 1)
                            nextx = nextx + prevx
                            nexty = nexty + prevy

                            Dim dist As Integer = (firstvx - nextx) * (firstvx - nextx)
                            dist = dist + (firstvy - nexty) * (firstvy - nexty)
                            If dist > 10000 Then
                                continueFlag = True
                                firstFlag = 0
                                pathLength(numPaths) = a_index
                                a_index = 0
                                cmd = "m"
                                numPaths = numPaths + 1
                            Else
                                isClosed(numPaths) = True
                                Exit For

                            End If

                        Else
                            isClosed(numPaths) = False
                            Exit For

                        End If
                    Case " "
                    Case "-"
                        sign = -1
                    Case Else
                        If filestr.Chars(i) <= "9" And filestr.Chars(i) >= "0" Then
                            Dim j As Integer = i
                            Dim val As Integer = 0
                            Dim zero As Integer = Convert.ToInt32(("0"c))
                            While filestr.Chars(j) <= "9" And filestr.Chars(j) >= "0"
                                val = Convert.ToInt32(filestr.Chars(j)) - zero + val * 10
                                j += 1
                            End While
                            val = val * sign
                            sign = 1
                            Dim wrt As Boolean = False
                            If cmd = "c" Then
                                If cflag = 5 Or cflag = 6 Then
                                    If rflag Then
                                        points(numPaths, a_index) = points(numPaths, a_index - 2) + val
                                    Else
                                        points(numPaths, a_index) = val
                                    End If
                                    If cflag = 5 Then
                                        cflag = 6
                                    Else
                                        cflag = 1
                                    End If
                                    wrt = True
                                Else
                                    cflag += 1
                                    wrt = False
                                End If
                            Else

                                If rflag Then
                                    If firstFlag < 2 And continueFlag Then
                                        points(numPaths, a_index) = val + points(numPaths - 1, pathLength(numPaths - 1) - 2 + firstFlag)
                                        firstFlag = firstFlag + 1
                                        If firstFlag = 2 Then
                                            continueFlag = False
                                        End If

                                    Else
                                        points(numPaths, a_index) = points(numPaths, a_index - 2) + val
                                    End If
                                Else
                                    points(numPaths, a_index) = val
                                End If
                                wrt = True
                            End If

                            i = j - 1
                            If wrt Then
                                a_index += 1
                            End If

                        End If
                End Select

            Next
            start_index = endIndex
            start_index = filestr.IndexOf(str, start_index)
            If start_index <= 0 Then
                doloop = False
            End If
            pathLength(numPaths) = a_index
            numPaths += 1
        End While

        ' When 180 degree turns are found in a polygon at a distance greater than a particular distance than we can assume that it is because
        ' the polygon has started backtracing itself to form a closed figure. So we can ignore it.
        For i As Integer = 0 To numPaths - 1
            Dim prevAngle As Double = GetAngle(points(i, 4) - points(i, 2), points(i, 5) - points(i, 3))
            Dim newlen As Double = 0
            Dim angleBtw As Double = 0
            Dim curAngle As Double = 0
            Dim revflag As Boolean = False
            Dim rightFlag As Boolean = False
            For j As Integer = 4 To pathLength(i) - 4 Step 2
                If points(i, j) = points(i, j + 2) And points(i, j + 1) = points(i, j + 3) Then
                    Continue For
                End If
                curAngle = GetAngle(points(i, j + 2) - points(i, j), points(i, j + 3) - points(i, j + 1))

                angleBtw = Math.Abs(curAngle - prevAngle)

                If angleBtw > 170 And angleBtw < 190 Then       ' The smaller the range, the more accurately you will remove
                    revflag = True                              ' the backturns but since these backturns are not exactly
                    newlen = j + 2                              ' 180 degrees, we need to have a margin of error
                    Exit For
                End If
                If angleBtw > 80 And angleBtw < 100 Then
                    If rightFlag Then
                        Dim dist As Integer = (points(i, j) - points(i, j - 2)) * (points(i, j) - points(i, j - 2))
                        dist = dist + (points(i, j + 1) - points(i, j - 1)) * (points(i, j + 1) - points(i, j - 1))
                        If dist < 10000 Then
                            revflag = True
                            newlen = j
                            Exit For
                        End If
                    End If
                    rightFlag = True
                Else
                    rightFlag = False
                End If

                prevAngle = curAngle
            Next
            If revflag Then
                pathLength(i) = newlen + 2
            End If
        Next

        Return 0
    End Function

End Class
