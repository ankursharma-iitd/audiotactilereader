Imports cannystillcop.Reader
Imports cannystillcop.Converter


Public Class frmMain
    
    Dim address As String                           ' Address of the location where the user wants to store the data of the book

    Dim BMP As New Drawing.Bitmap(1360, 640)
    Dim GFX As Graphics = Graphics.FromImage(BMP)

    Private MouseX, MouseY As Integer

    Dim read As Reader
    Dim conv As Converter
    Dim f As Double = 1             ' Scaling factor
    Dim manual As Boolean = False   ' Whether we are in the manual polygon creation mode or not
    Dim temp As Boolean = False     ' Whether the screen is showing a manually created temporary rectangle or not
    Dim lt As Point                 ' Left-top point
    Dim rb As Point                 ' Right-bottom point

    Dim filesloc(-1) As String
    Dim flist(-1) As face

    Dim folder As String
    Dim tempface As face

    Dim projectqr As Integer = 0
    Dim pageqr As Integer = 0

    Dim flag1 As Boolean = False
    '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

    ' WebBrowser1 displays the SVG file
    ' PictureBox1 displays the DCEL drawn on the picturebox
    ' PictureBox2 displays the selected face

    Public Sub Open(ByVal text As String)
        WebBrowser1.Visible = True
        PictureBox1.Visible = False
        PictureBox2.Visible = False
        f = 1
        manual = False
        temp = False
        WebBrowser1.DocumentText = text
    End Sub

    Private Sub AddPageToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles AddPageToolStripMenuItem.Click
        Dim drChosenFile As DialogResult

        drChosenFile = ofdOpenFile.ShowDialog()                 'open file dialog

        If (drChosenFile <> DialogResult.OK Or ofdOpenFile.FileName = "") Then    'if user chose Cancel or filename is blank . . .
            lblChosenFile.Text = "file not chosen"              'show error message on label
            Return                                              'and exit function
        End If

        Try
            address = ofdOpenFile.FileName
            Dim text As String = System.IO.File.ReadAllText(ofdOpenFile.FileName)
            Open(text)
            lblChosenFile.Text = ofdOpenFile.FileName
            ConvertToolStripMenuItem.Enabled = True
            pageqr = pageqr + 1
            ReDim filesloc(-1)
            ReDim flist(-1)
        Catch ex As Exception                                                       'if error occurred
            lblChosenFile.Text = "unable to open image, error: " + ex.Message       'show error message on label
            Return                                                                  'and exit function
        End Try

        If (ofdOpenFile.FileName Is Nothing) Then
            lblChosenFile.Text = "unable to open image"
            Return
        End If
    End Sub

    Private Sub OpenToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles OpenToolStripMenuItem.Click
        Dim name As String = InputBox("Please Enter the Project Name", "Project Name", " ")
        Dim tempname As String = ""
        If name = " " Then
            MessageBox.Show("Please Enter valid Project Name")
        ElseIf name = "" Then

        Else
            My.Computer.FileSystem.CreateDirectory("C:\Users\Public\Documents\COP")
            tempname = InputBox("Please Enter the Project Directory", "Add Directory", "C:\Users\Public\Documents\COP\" & name)
        End If
        If tempname <> "" Then
            folder = tempname
            f = 1
            manual = False
            temp = False
            OpenToolStripMenuItem.Enabled = True
            AddPageToolStripMenuItem.Enabled = True
            SaveAsToolStripMenuItem.Enabled = True
            ReDim filesloc(-1)
            ReDim flist(-1)
            My.Computer.FileSystem.CreateDirectory(folder)
            pageqr = 0
            Dim k As Integer = folder.Length - 1
            While folder.Chars(k) <> "\"c
                k = k - 1
            End While
            flag1 = False

            Try
                Dim filestr As String = My.Computer.FileSystem.ReadAllText(folder.Substring(0, k) & "\projectqr.txt")
                Dim zero As Integer = Convert.ToInt32(("0"c))
                projectqr = 0
                For j As Integer = 0 To filestr.Length() - 1
                    projectqr = Convert.ToInt32(filestr.Chars(j)) - zero + projectqr * 10
                Next
            Catch
                projectqr = 0
                My.Computer.FileSystem.WriteAllText(folder.Substring(0, k) & "\qrtable.txt", vbNewLine, False)
            End Try
            projectqr = projectqr + 1
            My.Computer.FileSystem.WriteAllText(folder.Substring(0, k) & "\projectqr.txt", projectqr.ToString(), False)
            My.Computer.FileSystem.WriteAllText(folder.Substring(0, k) & "\qrtable.txt", projectqr.ToString() & " " & folder.Substring(k + 1, folder.Length - k - 1) & vbNewLine, True)
        End If
    End Sub

    Private Sub ExitToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles ExitToolStripMenuItem.Click
        Close()
    End Sub

    Private Sub ConvertToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles ConvertToolStripMenuItem.Click
        WebBrowser1.Visible = False
        PictureBox2.Visible = False
        PictureBox1.Visible = True
        Data.Enabled = False
        Cancel.Enabled = False
        mdraw.Enabled = True
        manual = False
        temp = False
        f = 1
        dowhenstart()
    End Sub

    Public Function drawfull()
        PictureBox1.Image = Nothing
        GFX.FillRectangle(Brushes.White, 0, 0, PictureBox1.Width, PictureBox1.Height)
        Dim i As Integer = 0
        While i < read.edgelist.Length
            Dim blackPen As New Pen(Color.Black, 3)
            Dim point1 As New Point(read.edgelist(i).tail.ver.x / f, read.edgelist(i).tail.ver.y / f)
            Dim point2 As New Point(read.edgelist(i).twin.tail.ver.x / f, read.edgelist(i).twin.tail.ver.y / f)
            GFX.DrawLine(blackPen, point1, point2)
            i = i + 2
        End While
        PictureBox1.Image = BMP
        PictureBox1.Visible = True
        PictureBox2.Visible = False
        Return 0
    End Function

    Public Sub dowhenstart()
        conv = New Converter
        conv.Main(address)
        read = New Reader
        read.start(conv.points, conv.pathLength, conv.isClosed, conv.numPaths)
        GFX.FillRectangle(Brushes.White, 0, 0, PictureBox1.Width, PictureBox1.Height)
        If conv.hei > 640 Then
            f = conv.hei / 640
        End If
        If conv.wid / f > 1360 Then
            f = conv.wid / 1360
        End If
        drawfull()
    End Sub

    Private Sub Cancel_Click(sender As Object, e As EventArgs) Handles Cancel.Click
        PictureBox1.Visible = True
        PictureBox2.Visible = False
        Data.Enabled = False
        Cancel.Enabled = False
        mdraw.Enabled = True
        manual = False
        temp = False
        mdraw.Text = "Add Manually"
        drawfull()
    End Sub

    Private Sub mdraw_Click(sender As Object, e As EventArgs) Handles mdraw.Click
        If mdraw.Text = "Confirm" Then
            mdraw.Text = "Add Manually"
            manual = False
            Cancel.Enabled = False
            Dim loc(3) As location
            loc(0).x = lt.X * f
            loc(0).y = lt.Y * f
            loc(1).x = rb.X * f
            loc(1).y = lt.Y * f
            loc(2).x = rb.X * f
            loc(2).y = rb.Y * f
            loc(3).x = lt.X * f
            loc(3).y = rb.Y * f
            read.addface(loc)
            drawfull()
        Else
            manual = True
            mdraw.Text = "Confirm"
            Cancel.Enabled = True
        End If
    End Sub

    Private Sub PictureBox1_Mouseup(ByVal sender As Object, ByVal e As System.Windows.Forms.MouseEventArgs) Handles PictureBox1.MouseUp
        If manual = True And e.Button = MouseButtons.Left Then
            temp = False
            Dim redPen As New Pen(Color.Red, 1)
            Dim point1 As New Point(lt.X, lt.Y)
            Dim point2 As New Point(e.X, lt.Y)
            Dim point3 As New Point(e.X, e.Y)
            Dim point4 As New Point(lt.X, e.Y)
            GFX.DrawLine(redPen, point1, point2)
            GFX.DrawLine(redPen, point2, point3)
            GFX.DrawLine(redPen, point3, point4)
            GFX.DrawLine(redPen, point4, point1)
            PictureBox1.Image = BMP
            Me.Refresh()
        End If
    End Sub

    Private Sub PictureBox1_Mousemove(ByVal sender As Object, ByVal e As System.Windows.Forms.MouseEventArgs) Handles PictureBox1.MouseMove
        If manual = True And e.Button = MouseButtons.Left Then
            rb = e.Location
            temp = True
            Me.Refresh()
        End If
    End Sub

    Private Sub Data_Click(sender As Object, e As EventArgs) Handles Data.Click
        Dim drChosenFile As DialogResult

        drChosenFile = ofdOpenFile.ShowDialog()                 'open file dialog

        If (drChosenFile <> DialogResult.OK Or ofdOpenFile.FileName = "") Then
            lblChosenFile.Text = "file not chosen"
            Return
        End If

        Try
            lblChosenFile.Text = ofdOpenFile.FileName
            ReDim Preserve filesloc(UBound(filesloc) + 1)
            filesloc(UBound(filesloc)) = ofdOpenFile.FileName
            Console.WriteLine(ofdOpenFile.FileName)
            Console.WriteLine(filesloc(UBound(filesloc)))
            ReDim Preserve flist(UBound(flist) + 1)
            flist(UBound(flist)) = tempface
            Data.Enabled = False
            Cancel.Enabled = False
            mdraw.Enabled = True
            manual = False
            temp = False
            drawfull()
        Catch ex As Exception                                                       'if error occurred
            lblChosenFile.Text = "unable to open the file, error: " + ex.Message       'show error message on label
            Return                                                                  'and exit function
        End Try

        If (ofdOpenFile.FileName Is Nothing) Then
            lblChosenFile.Text = "Unable to open the file"                 'show error message on label
            Return                                                      'and exit function
        End If
    End Sub

    Private Sub SaveAsToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles SaveAsToolStripMenuItem.Click
        Dim k As Integer = address.Length - 1
        While address.Chars(k) <> "\"c
            k = k - 1
        End While
        My.Computer.FileSystem.CreateDirectory(folder & address.Substring(k, address.Length - k - 4))
        For i As Integer = 0 To filesloc.Length - 1
            Dim temp As String = filesloc(i)
            Dim j As Integer = temp.Length - 1
            While temp.Chars(j) <> "\"c
                j = j - 1
            End While
            If temp.Chars(temp.Length - 1) = "t" Then
                conv.texttospeech(temp, folder & address.Substring(k, address.Length - k - 4) & temp.Substring(j, temp.Length - j - 4) & ".wav")
            Else
                My.Computer.FileSystem.CopyFile(temp, folder & address.Substring(k, address.Length - k - 4) & temp.Substring(j),
                Microsoft.VisualBasic.FileIO.UIOption.AllDialogs,
                Microsoft.VisualBasic.FileIO.UICancelOption.DoNothing)
            End If
        Next
        conv.WriteData(flist, filesloc, folder & address.Substring(k, address.Length - k - 4))
        If flag1 = False Then
            My.Computer.FileSystem.WriteAllText(folder & "\qrtable.txt", vbNewLine, False)
            flag1 = True
        End If
        My.Computer.FileSystem.WriteAllText(folder & "\qrtable.txt", pageqr.ToString() & " " & address.Substring(k + 1, address.Length - k - 5) & vbNewLine, True)
        WebBrowser1.DocumentText = Nothing
        WebBrowser1.Visible = True
        PictureBox1.Visible = False
        PictureBox2.Visible = False
        ConvertToolStripMenuItem.Enabled = False
    End Sub

    Private Sub PictureBox1_MouseDown(ByVal sender As Object, ByVal e As System.Windows.Forms.MouseEventArgs) Handles PictureBox1.MouseDown
        MouseX = e.X
        MouseY = e.Y
        If manual = False Then
            Dim loc As location = New location
            loc.x = MouseX
            loc.y = MouseY
            Dim selpolygon As face
            selpolygon = read.pointin_p(loc, f)
            If selpolygon IsNot Nothing Then
                PictureBox2.Visible = True
                PictureBox1.Visible = False
                GFX.FillRectangle(Brushes.White, 0, 0, PictureBox2.Width, PictureBox2.Height)
                Dim start As half_edge = selpolygon.rep
                Dim curr = start.nex
                If curr IsNot Nothing Then
                    Dim blackPen As New Pen(Color.Black, 3)
                    Dim point1 As New Point(curr.tail.ver.x / f, curr.tail.ver.y / f)
                    Dim point2 As New Point(start.tail.ver.x / f, start.tail.ver.y / f)
                    GFX.DrawLine(blackPen, point1, point2)
                End If
                While start IsNot curr And curr IsNot Nothing
                    Dim blackPen As New Pen(Color.Black, 3)
                    Dim point1 As New Point(curr.tail.ver.x / f, curr.tail.ver.y / f)
                    Dim point2 As New Point(curr.twin.tail.ver.x / f, curr.twin.tail.ver.y / f)
                    GFX.DrawLine(blackPen, point1, point2)
                    curr = curr.nex
                End While
                PictureBox2.Image = BMP
                Cancel.Enabled = True
                Data.Enabled = True
                mdraw.Enabled = False
                tempface = selpolygon
            End If
        ElseIf e.Button = MouseButtons.Left Then
            drawfull()
            lt = e.Location
        End If
    End Sub
End Class
