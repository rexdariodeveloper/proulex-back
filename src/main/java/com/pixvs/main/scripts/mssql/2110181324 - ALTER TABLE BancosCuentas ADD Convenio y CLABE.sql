/**
* Created by Angel Daniel Hernández Silva on 15/10/2021.
* Object: ALTER TABLE [dbo].[BancosCuentas] ADD [BAC_Convenio], [BAC_CLABE]
*/

ALTER TABLE [dbo].[BancosCuentas] ADD [BAC_Convenio] varchar(10) NULL
GO

ALTER TABLE [dbo].[BancosCuentas] ADD [BAC_CLABE] varchar(20) NULL
GO

UPDATE BancosCuentas SET BAC_CLABE = '014320655007508639', BAC_Convenio = NULL WHERE BAC_Codigo = '65500750863'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007508778', BAC_Convenio = NULL WHERE BAC_Codigo = '65500750877'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007508804', BAC_Convenio = '9598' WHERE BAC_Codigo = '65500750880'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007512926', BAC_Convenio = '5061' WHERE BAC_Codigo = '65500751292'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007535521', BAC_Convenio = '9554' WHERE BAC_Codigo = '65500753552'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007582118', BAC_Convenio = '7844' WHERE BAC_Codigo = '65500758211'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007791941', BAC_Convenio = '9564' WHERE BAC_Codigo = '65500779194'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007792063', BAC_Convenio = '9562' WHERE BAC_Codigo = '65500779206'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007792238', BAC_Convenio = '9557' WHERE BAC_Codigo = '65500779223'
UPDATE BancosCuentas SET BAC_CLABE = '014320655007799901', BAC_Convenio = '9566' WHERE BAC_Codigo = '65500779990'
UPDATE BancosCuentas SET BAC_CLABE = '014320655008080211', BAC_Convenio = '9565' WHERE BAC_Codigo = '65500808021'
UPDATE BancosCuentas SET BAC_CLABE = '014320655008164250', BAC_Convenio = '9560' WHERE BAC_Codigo = '65500816425'
UPDATE BancosCuentas SET BAC_CLABE = '014320655008210269', BAC_Convenio = '9567' WHERE BAC_Codigo = '65500821026'
UPDATE BancosCuentas SET BAC_CLABE = '014320655008371470', BAC_Convenio = '9559' WHERE BAC_Codigo = '65500837147'
UPDATE BancosCuentas SET BAC_CLABE = '014320655022035336', BAC_Convenio = '9558' WHERE BAC_Codigo = '65502203533'
UPDATE BancosCuentas SET BAC_CLABE = '014320655030066410', BAC_Convenio = '9555' WHERE BAC_Codigo = '65503006641'
UPDATE BancosCuentas SET BAC_CLABE = '014320655032858998', BAC_Convenio = '9561' WHERE BAC_Codigo = '65503285899'
UPDATE BancosCuentas SET BAC_CLABE = '014320655032859599', BAC_Convenio = '9552' WHERE BAC_Codigo = '65503285959'
UPDATE BancosCuentas SET BAC_CLABE = '014320655032860847', BAC_Convenio = '9553' WHERE BAC_Codigo = '65503286084'
UPDATE BancosCuentas SET BAC_CLABE = '014320665032861309', BAC_Convenio = '9563' WHERE BAC_Codigo = '65503286130'
UPDATE BancosCuentas SET BAC_CLABE = '014320655044017293', BAC_Convenio = '9596' WHERE BAC_Codigo = '65504401729'
UPDATE BancosCuentas SET BAC_CLABE = '014320655048943402', BAC_Convenio = '9556' WHERE BAC_Codigo = '65504894340'
UPDATE BancosCuentas SET BAC_CLABE = '014320655055658463', BAC_Convenio = NULL WHERE BAC_Codigo = '65505565846'
UPDATE BancosCuentas SET BAC_CLABE = '014320655067580082', BAC_Convenio = '1088' WHERE BAC_Codigo = '65506758008'
UPDATE BancosCuentas SET BAC_CLABE = '014320655071443733', BAC_Convenio = '6518' WHERE BAC_Codigo = '65507144373'
UPDATE BancosCuentas SET BAC_CLABE = '014320655074977235', BAC_Convenio = NULL WHERE BAC_Codigo = '65507497723'
UPDATE BancosCuentas SET BAC_CLABE = '014320655087164718', BAC_Convenio = NULL WHERE BAC_Codigo = '65508716471'
UPDATE BancosCuentas SET BAC_CLABE = '012320001915781893', BAC_Convenio = NULL WHERE BAC_Codigo = '0191578189'
UPDATE BancosCuentas SET BAC_CLABE = '021320040217353117', BAC_Convenio = NULL WHERE BAC_Codigo = '04021735311'
GO

INSERT INTO [dbo].[BancosCuentas](
	[BAC_Codigo],
	[BAC_Descripcion],
	[BAC_MON_MonedaId],
	[BAC_BAN_BancoId],
	[BAC_Activo],
	[BAC_USU_CreadoPorId],
	[BAC_FechaCreacion],
	[BAC_CLABE]
) VALUES(
	'65508716471',
	'INSTITUTO CONFUCIO',
	1,
	1,
	1,
	1,
	GETDATE(),
	'014320655087164718'
)
GO