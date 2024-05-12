@set jp=D:\__kko\j2sdk1.4.2_03\bin\

:@pause

@%jp%javac.exe -classpath comm20.jar serv.java
@pause
@%jp%\java.exe -classpath comm20.jar;. serv
@pause