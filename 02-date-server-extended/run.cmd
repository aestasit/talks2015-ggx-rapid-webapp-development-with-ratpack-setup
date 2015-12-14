
@echo off

@set RATPACK_PORT=5555
@set RATPACK_DEVELOPMENT=true

@rem set JAVA_OPTS=-Dgroovy.grape.report.downloads=true 
@set JAVA_OPTS=

@set JAVA_HOME=C:\JVMs\jdk1.8.0_45
@set PATH=%JAVA_HOME%\bin;%PATH%

@start groovy DateServer.groovy 