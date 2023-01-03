@echo off
SetLocal EnableDelayedExpansion
chcp 65001

ECHO ---------------------------------------------------------------------- 
ECHO Bat Correr Scripts
ECHO ---------------------------------------------------------------------- 

SET _server=LOCALHOST
SET _sqlPort=1433
SET _sqlUser=sa
SET _sqlBD=PROULEX
SET _sqlPass=c4224a

set cont=0

ECHO ---------------------------------------------------------------------- 
ECHO %_sqlBD%
ECHO ---------------------------------------------------------------------- 

SET _sqlQueyLast="USE %_sqlBD%; SET NOCOUNT ON;  DECLARE @emp VARCHAR(100) SET @emp = (SELECT CMA_Valor FROM ControlesMaestros WHERE CMA_Nombre ='CM_NOMBRE') DECLARE @version VARCHAR(100) SET @version = (SELECT CMA_Valor FROM ControlesMaestros WHERE CMA_Nombre ='CM_SYS_SCRIPTS') PRINT @version"

:LoopLast
for /f %%a in ('sqlcmd -U %_sqlUser% -P %_sqlPass% -m 1 /S %_server%^,%_sqlPort% /d %_sqlBD% -Q %_sqlQueyLast%') do (
	set _last=%%a
)

echo %_last%

set _lastScript=!_last:~2,8!
set _lastScriptYear=!_last:~0,2!


ECHO ---------------------------------------------------------------------- 
ECHO Ultimo Script
ECHO %_lastScriptYear% - %_lastScript%
ECHO ---------------------------------------------------------------------- 

pause
:Loop 
For %%X in (*.sql) do ( 

	set _sqlFile=%%X
	set _sqlNumber=!_sqlFile:~2,8!
	set _sqlYear=!_sqlFile:~0,2!

	set _true=0


	if !_sqlYear! GEQ !_lastScriptYear!  (

		if !_sqlYear! GTR !_lastScriptYear! (set _true=1)
		if !_sqlNumber! GTR !_lastScript! (set _true=1)

		if !_true! EQU 1 (

			ECHO * & echo.* & echo.****************************************************** & echo.%%X & echo.******************************************************  
			SQLCMD -U %_sqlUser% -P %_sqlPass% -m 1 /S %_server%,%_sqlPort% /d %_sqlBD% -I -i "%%X" -f 65001 && PAUSE
			ECHO ----------------------------------------------------------------------
			PAUSE
			SQLCMD -U %_sqlUser% -P %_sqlPass% -m 1 /S %_server%,%_sqlPort% /d %_sqlBD% -Q "USE %_sqlBD%  UPDATE ControlesMaestros SET CMA_Valor = '!_sqlFile!' , CMA_FechaModificacion = GETDATE() WHERE CMA_Nombre ='CM_SYS_SCRIPTS'  "
			set /A cont=!cont!+1
		)

	)
)

ECHO ---------------------------------------------------------------------- 
ECHO Scripts Corridos
ECHO (%cont% Scripts)
ECHO ---------------------------------------------------------------------- 


pause