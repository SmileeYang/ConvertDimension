-------------------------------------------------------------
This tool is about convert dimensions of Android project which in 
res/values-swXXXdp/dimens.xml and convert to the others.

Now available: sw320dp, sw360dp, sw600dp, sw720dp and sw800dp.

Developed by Smilee with JAVA programming @2014/09/01.

Modified in 2014/09/03.
-------------------------------------------------------------

Introduction：

	This ConvertDimen.jar file was a runnable JAR file, which support you to convert dimension of res/values-swXXXdp/dimens.xml in Android project. Because many devices are using Android as their OS, and Android wants to support each kinds of these device with different screen size and density. So Android use dimens.xml with sw(which means - smallest width)XXX(which can be 320, 360, 600, 720, 800.etc)dp, to achieve this goal.
	
	This tools allows you to convert different dimensions in dimens.xml between sw320dp, sw360dp, sw600dp, sw720dp and sw800dp. Which means if you give a res/values-sw320dp/dimens.xml, and you can get four more files which are values-sw360dp/dimens.xml, values-sw600dp/dimens.xml, values-sw720dp/dimens.xml, and values-sw800dp/dimens.xml. 
	
	An example shows below:
・Gives: 
	<dimen name="abc">1dp</dimen> in res/values-sw320dp/dimens.xml
・Get: 
	<dimen name="abc">1.125dp</dimen> in res/values-sw360dp/dimens.xml
	<dimen name="abc">1.875dp</dimen> in res/values-sw600dp/dimens.xml
	<dimen name="abc">2.25dp</dimen> in res/values-sw720dp/dimens.xml
	<dimen name="abc">2.5dp</dimen> in res/values-sw800dp/dimens.xml

How to use:

	1. Download this ConvertDimen.jar file to local, and start a cmd. 
	2. Switch to this file's located directory and type the following command line then press enter:
		$ java -jar ConvertDimen.jar 
	3. This tool will show a message if the command was correct:
		・Please enter the original dimens.xml file path:
	4. Please enter your dimens.xml located path like: 
		・/Users/smilee_yang/Documents/workspace/SupportDifferentSize/res/values-sw320dp/dimens.xml
	5. If the file path is correct, it will show some message:
		・-------------------------
		・mDimenName: line_height & nowDimenValue=1dip
		・mDimenName: text_size & nowDimenValue=6sp
		・mDimenName: tv_title_size & nowDimenValue=8sp
		・mDimenName: micro_text_size & nowDimenValue=12sp
		・mDimenName: small_text_size & nowDimenValue=14sp
		・mDimenName: medium_text_size & nowDimenValue=16sp
		・mDimenName: large_text_size & nowDimenValue=18sp
		・mDimenName: xlarge_text_size & nowDimenValue=20sp
		・mDimenName: xxlarge_text_size & nowDimenValue=22sp
		・mDimenName: xxxlarge_text_size & nowDimenValue=24sp
		・mDimenName: now_dp & nowDimenValue=10dp
		・-------------------------
		・This file is based on - 1.sw320dp 2.sw360dp 3.sw600dp 4.sw720dp 5.sw800dp (plz enter the number):  
	*In my case, my dimens.xml includes 11 dimension elements.
	*The savingPath means when this convert process was done, the new dimension files will save at this path.
	6. And I need to enter "1" in this case, but choose a correct type in your case!! 
	   After entering 1, will show this message:
		・Start convert process, convert from sw320dp to the others.
		・Convert success! Now saving path is: /Users/smilee_yang/Documents/workspace/SupportDifferentSize/res
		・Do you want to save these files with this path? (Y/N)
	7. Then, you should choose your saving path is the path that program parse(by entering: Y or y), or a new path(by entering: N or n).
	***if entering Y:
		・Confirmed your saving path is: /Users/smilee_yang/Documents/workspace/SupportDifferentSize/res.
		 When the process was done, will show the success message: 		・Convert process completed.
	***else if entering N:
		・Please enter a path that you want to save these files: 
		And you should give a correct path to save these files. After that...
		・Correct path. Convert process completed.
	8. Then you can check your new dimens.xml files!!
	