<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()>
Partial Class frmMain
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()>
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()>
    Private Sub InitializeComponent()
        Me.tlpOuter = New System.Windows.Forms.TableLayoutPanel()
        Me.lblChosenFile = New System.Windows.Forms.Label()
        Me.tlpInner = New System.Windows.Forms.TableLayoutPanel()
        Me.MenuStrip1 = New System.Windows.Forms.MenuStrip()
        Me.FileToolStripMenuItem = New System.Windows.Forms.ToolStripMenuItem()
        Me.OpenToolStripMenuItem = New System.Windows.Forms.ToolStripMenuItem()
        Me.AddPageToolStripMenuItem = New System.Windows.Forms.ToolStripMenuItem()
        Me.ConvertToolStripMenuItem = New System.Windows.Forms.ToolStripMenuItem()
        Me.SaveAsToolStripMenuItem = New System.Windows.Forms.ToolStripMenuItem()
        Me.ExitToolStripMenuItem = New System.Windows.Forms.ToolStripMenuItem()
        Me.Panel1 = New System.Windows.Forms.Panel()
        Me.WebBrowser1 = New System.Windows.Forms.WebBrowser()
        Me.PictureBox2 = New System.Windows.Forms.PictureBox()
        Me.PictureBox1 = New System.Windows.Forms.PictureBox()
        Me.Cancel = New System.Windows.Forms.Button()
        Me.Data = New System.Windows.Forms.Button()
        Me.mdraw = New System.Windows.Forms.Button()
        Me.ofdOpenFile = New System.Windows.Forms.OpenFileDialog()
        Me.ofdSaveFile = New System.Windows.Forms.SaveFileDialog()
        Me.tlpOuter.SuspendLayout()
        Me.tlpInner.SuspendLayout()
        Me.MenuStrip1.SuspendLayout()
        Me.Panel1.SuspendLayout()
        CType(Me.PictureBox2, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.PictureBox1, System.ComponentModel.ISupportInitialize).BeginInit()
        Me.SuspendLayout()
        '
        'tlpOuter
        '
        Me.tlpOuter.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.[Single]
        Me.tlpOuter.ColumnCount = 4
        Me.tlpOuter.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle())
        Me.tlpOuter.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle())
        Me.tlpOuter.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle())
        Me.tlpOuter.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle())
        Me.tlpOuter.Controls.Add(Me.lblChosenFile, 2, 0)
        Me.tlpOuter.Controls.Add(Me.tlpInner, 0, 0)
        Me.tlpOuter.Controls.Add(Me.Panel1, 0, 2)
        Me.tlpOuter.Controls.Add(Me.Cancel, 1, 1)
        Me.tlpOuter.Controls.Add(Me.Data, 0, 1)
        Me.tlpOuter.Controls.Add(Me.mdraw, 2, 1)
        Me.tlpOuter.Dock = System.Windows.Forms.DockStyle.Fill
        Me.tlpOuter.Location = New System.Drawing.Point(0, 0)
        Me.tlpOuter.Name = "tlpOuter"
        Me.tlpOuter.RowCount = 3
        Me.tlpOuter.RowStyles.Add(New System.Windows.Forms.RowStyle())
        Me.tlpOuter.RowStyles.Add(New System.Windows.Forms.RowStyle())
        Me.tlpOuter.RowStyles.Add(New System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100.0!))
        Me.tlpOuter.Size = New System.Drawing.Size(798, 441)
        Me.tlpOuter.TabIndex = 0
        '
        'lblChosenFile
        '
        Me.lblChosenFile.AutoSize = True
        Me.tlpOuter.SetColumnSpan(Me.lblChosenFile, 2)
        Me.lblChosenFile.Dock = System.Windows.Forms.DockStyle.Fill
        Me.lblChosenFile.Font = New System.Drawing.Font("Microsoft Sans Serif", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.lblChosenFile.Location = New System.Drawing.Point(168, 1)
        Me.lblChosenFile.Name = "lblChosenFile"
        Me.lblChosenFile.Size = New System.Drawing.Size(626, 30)
        Me.lblChosenFile.TabIndex = 7
        '
        'tlpInner
        '
        Me.tlpInner.AutoSize = True
        Me.tlpInner.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink
        Me.tlpInner.ColumnCount = 2
        Me.tlpOuter.SetColumnSpan(Me.tlpInner, 2)
        Me.tlpInner.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle())
        Me.tlpInner.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100.0!))
        Me.tlpInner.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20.0!))
        Me.tlpInner.ColumnStyles.Add(New System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20.0!))
        Me.tlpInner.Controls.Add(Me.MenuStrip1, 0, 0)
        Me.tlpInner.Dock = System.Windows.Forms.DockStyle.Fill
        Me.tlpInner.Location = New System.Drawing.Point(4, 4)
        Me.tlpInner.Name = "tlpInner"
        Me.tlpInner.RowCount = 1
        Me.tlpInner.RowStyles.Add(New System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100.0!))
        Me.tlpInner.Size = New System.Drawing.Size(157, 24)
        Me.tlpInner.TabIndex = 0
        '
        'MenuStrip1
        '
        Me.MenuStrip1.Dock = System.Windows.Forms.DockStyle.Fill
        Me.MenuStrip1.Items.AddRange(New System.Windows.Forms.ToolStripItem() {Me.FileToolStripMenuItem})
        Me.MenuStrip1.Location = New System.Drawing.Point(0, 0)
        Me.MenuStrip1.Name = "MenuStrip1"
        Me.MenuStrip1.Size = New System.Drawing.Size(137, 24)
        Me.MenuStrip1.TabIndex = 4
        Me.MenuStrip1.Text = "MenuStrip1"
        '
        'FileToolStripMenuItem
        '
        Me.FileToolStripMenuItem.DropDownItems.AddRange(New System.Windows.Forms.ToolStripItem() {Me.OpenToolStripMenuItem, Me.AddPageToolStripMenuItem, Me.ConvertToolStripMenuItem, Me.SaveAsToolStripMenuItem, Me.ExitToolStripMenuItem})
        Me.FileToolStripMenuItem.Name = "FileToolStripMenuItem"
        Me.FileToolStripMenuItem.Size = New System.Drawing.Size(37, 20)
        Me.FileToolStripMenuItem.Text = "File"
        '
        'OpenToolStripMenuItem
        '
        Me.OpenToolStripMenuItem.Name = "OpenToolStripMenuItem"
        Me.OpenToolStripMenuItem.Size = New System.Drawing.Size(152, 22)
        Me.OpenToolStripMenuItem.Text = "Open Project"
        '
        'AddPageToolStripMenuItem
        '
        Me.AddPageToolStripMenuItem.Enabled = False
        Me.AddPageToolStripMenuItem.Name = "AddPageToolStripMenuItem"
        Me.AddPageToolStripMenuItem.Size = New System.Drawing.Size(152, 22)
        Me.AddPageToolStripMenuItem.Text = "Add Page"
        '
        'ConvertToolStripMenuItem
        '
        Me.ConvertToolStripMenuItem.Enabled = False
        Me.ConvertToolStripMenuItem.Name = "ConvertToolStripMenuItem"
        Me.ConvertToolStripMenuItem.Size = New System.Drawing.Size(152, 22)
        Me.ConvertToolStripMenuItem.Text = "Convert"
        '
        'SaveAsToolStripMenuItem
        '
        Me.SaveAsToolStripMenuItem.Enabled = False
        Me.SaveAsToolStripMenuItem.Name = "SaveAsToolStripMenuItem"
        Me.SaveAsToolStripMenuItem.Size = New System.Drawing.Size(152, 22)
        Me.SaveAsToolStripMenuItem.Text = "Export"
        '
        'ExitToolStripMenuItem
        '
        Me.ExitToolStripMenuItem.Name = "ExitToolStripMenuItem"
        Me.ExitToolStripMenuItem.Size = New System.Drawing.Size(152, 22)
        Me.ExitToolStripMenuItem.Text = "Exit"
        '
        'Panel1
        '
        Me.tlpOuter.SetColumnSpan(Me.Panel1, 4)
        Me.Panel1.Controls.Add(Me.WebBrowser1)
        Me.Panel1.Controls.Add(Me.PictureBox2)
        Me.Panel1.Controls.Add(Me.PictureBox1)
        Me.Panel1.Dock = System.Windows.Forms.DockStyle.Fill
        Me.Panel1.Location = New System.Drawing.Point(4, 65)
        Me.Panel1.Name = "Panel1"
        Me.Panel1.Size = New System.Drawing.Size(790, 372)
        Me.Panel1.TabIndex = 5
        '
        'WebBrowser1
        '
        Me.WebBrowser1.Dock = System.Windows.Forms.DockStyle.Fill
        Me.WebBrowser1.Location = New System.Drawing.Point(0, 0)
        Me.WebBrowser1.MinimumSize = New System.Drawing.Size(20, 20)
        Me.WebBrowser1.Name = "WebBrowser1"
        Me.WebBrowser1.Size = New System.Drawing.Size(790, 372)
        Me.WebBrowser1.TabIndex = 4
        '
        'PictureBox2
        '
        Me.PictureBox2.Dock = System.Windows.Forms.DockStyle.Fill
        Me.PictureBox2.Location = New System.Drawing.Point(0, 0)
        Me.PictureBox2.Name = "PictureBox2"
        Me.PictureBox2.Size = New System.Drawing.Size(790, 372)
        Me.PictureBox2.TabIndex = 6
        Me.PictureBox2.TabStop = False
        '
        'PictureBox1
        '
        Me.PictureBox1.Dock = System.Windows.Forms.DockStyle.Fill
        Me.PictureBox1.Location = New System.Drawing.Point(0, 0)
        Me.PictureBox1.Name = "PictureBox1"
        Me.PictureBox1.Size = New System.Drawing.Size(790, 372)
        Me.PictureBox1.TabIndex = 5
        Me.PictureBox1.TabStop = False
        '
        'Cancel
        '
        Me.Cancel.Cursor = System.Windows.Forms.Cursors.Default
        Me.Cancel.Dock = System.Windows.Forms.DockStyle.Fill
        Me.Cancel.Enabled = False
        Me.Cancel.Location = New System.Drawing.Point(86, 35)
        Me.Cancel.Name = "Cancel"
        Me.Cancel.Size = New System.Drawing.Size(75, 23)
        Me.Cancel.TabIndex = 6
        Me.Cancel.Text = "Cancel"
        Me.Cancel.UseVisualStyleBackColor = True
        '
        'Data
        '
        Me.Data.Cursor = System.Windows.Forms.Cursors.Default
        Me.Data.Dock = System.Windows.Forms.DockStyle.Fill
        Me.Data.Enabled = False
        Me.Data.Location = New System.Drawing.Point(4, 35)
        Me.Data.Name = "Data"
        Me.Data.Size = New System.Drawing.Size(75, 23)
        Me.Data.TabIndex = 8
        Me.Data.Text = "Add data"
        Me.Data.UseVisualStyleBackColor = True
        '
        'mdraw
        '
        Me.mdraw.AutoSize = True
        Me.mdraw.Cursor = System.Windows.Forms.Cursors.Default
        Me.mdraw.Dock = System.Windows.Forms.DockStyle.Fill
        Me.mdraw.Enabled = False
        Me.mdraw.Location = New System.Drawing.Point(168, 35)
        Me.mdraw.Name = "mdraw"
        Me.mdraw.Size = New System.Drawing.Size(80, 23)
        Me.mdraw.TabIndex = 9
        Me.mdraw.Text = "Add manually"
        Me.mdraw.UseVisualStyleBackColor = True
        '
        'ofdOpenFile
        '
        Me.ofdOpenFile.FileName = "OpenFileDialog1"
        '
        'frmMain
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(798, 441)
        Me.Controls.Add(Me.tlpOuter)
        Me.MainMenuStrip = Me.MenuStrip1
        Me.Name = "frmMain"
        Me.Text = "d"
        Me.tlpOuter.ResumeLayout(False)
        Me.tlpOuter.PerformLayout()
        Me.tlpInner.ResumeLayout(False)
        Me.tlpInner.PerformLayout()
        Me.MenuStrip1.ResumeLayout(False)
        Me.MenuStrip1.PerformLayout()
        Me.Panel1.ResumeLayout(False)
        CType(Me.PictureBox2, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.PictureBox1, System.ComponentModel.ISupportInitialize).EndInit()
        Me.ResumeLayout(False)

    End Sub

    Friend WithEvents tlpOuter As TableLayoutPanel
    Friend WithEvents tlpInner As TableLayoutPanel
    Friend WithEvents ofdOpenFile As OpenFileDialog
    Friend WithEvents MenuStrip1 As MenuStrip
    Friend WithEvents FileToolStripMenuItem As ToolStripMenuItem
    Friend WithEvents OpenToolStripMenuItem As ToolStripMenuItem
    Friend WithEvents SaveAsToolStripMenuItem As ToolStripMenuItem
    Friend WithEvents ofdSaveFile As SaveFileDialog
    Friend WithEvents ConvertToolStripMenuItem As ToolStripMenuItem
    Friend WithEvents ExitToolStripMenuItem As ToolStripMenuItem
    Friend WithEvents WebBrowser1 As WebBrowser
    Friend WithEvents Panel1 As Panel
    Friend WithEvents PictureBox1 As PictureBox
    Friend WithEvents lblChosenFile As Label
    Friend WithEvents Cancel As Button
    Friend WithEvents Data As Button
    Friend WithEvents mdraw As Button
    Friend WithEvents PictureBox2 As PictureBox
    Friend WithEvents AddPageToolStripMenuItem As ToolStripMenuItem
End Class
