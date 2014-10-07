//定义一种常用的自己的工具栏
FCKConfig.ToolbarSets["mgTool"] = [
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['TextColor','BGColor'],
	['Image','Table','Rule','Smiley','SpecialChar'],
	'/',
	['Style','FontFormat','FontName','FontSize'],
	
] ;

FCKConfig.ToolbarSets["smile"] = [
	['Smiley']
] ;

//修改皮肤--有三种选择defalut（默认），office2003,silver
FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/office2003/' ;
//增加集中常用的字体
FCKConfig.FontNames	= '宋体;华文彩云;微软雅黑;Arial;Verdana' ;
//修改回车换行
FCKConfig.EnterMode = 'br';      // p | div | br
FCKConfig.ShiftEnterMode = 'p';

//更改表情图片
FCKConfig.SmileyPath	= FCKConfig.BasePath + 'images/smiley/wangwang/' ;
FCKConfig.SmileyImages	= ['0.gif','1.gif','2.gif','3.gif','4.gif','5.gif','6.gif','7.gif','8.gif','9.gif','10.gif',
							'11.gif','12.gif','13.gif','14.gif','15.gif','16.gif','17.gif','18.gif','19.gif','20.gif',
							'21.gif','22.gif','23.gif','24.gif','25.gif','26.gif','27.gif','28.gif','29.gif','20.gif',
							'31.gif','32.gif','33.gif','34.gif','35.gif','36.gif','37.gif','38.gif','39.gif','40.gif',
							'41.gif','42.gif','43.gif','44.gif','45.gif','46.gif','47.gif','48.gif','49.gif','50.gif',
							'51.gif','52.gif','53.gif','54.gif','55.gif','56.gif','57.gif','58.gif','59.gif','60.gif',
							'61.gif','62.gif','63.gif','64.gif','65.gif','66.gif','67.gif','68.gif','69.gif','70.gif',
							'71.gif','72.gif','73.gif','74.gif','75.gif','76.gif','77.gif','78.gif','79.gif','80.gif',
							'81.gif','82.gif','83.gif','84.gif','85.gif','86.gif','87.gif'] ;
FCKConfig.SmileyColumns = 8;
FCKConfig.SmileyWindowWidth	= 660;
FCKConfig.SmileyWindowHeight= 480 ;
//要加上滚动条，需要将该窗口对应的文件中的//dialog.SetAutoSize( true ) ;注释掉，并将body的overflow:auto;

//配置允许上传的图像格式
FCKConfig.ImageUpload = true ;
FCKConfig.ImageUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Image' ;
FCKConfig.ImageUploadAllowedExtensions	= ".(jpg|gif|jpeg|png|bmp)$" ;		// empty for all
FCKConfig.ImageUploadDeniedExtensions	= "" ;