SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_Listado_Alumnos]
AS
SELECT ALU_AlumnoId AS id,
       ALU_Codigo AS codigo,
       ALU_Nombre AS nombre,
       ALU_PrimerApellido+COALESCE(' '+ALU_SegundoApellido, '') AS apellidos,
       ALU_Nombre+' '+ALU_PrimerApellido+COALESCE(' '+ALU_SegundoApellido, '')+' '+ALU_Nombre AS nombreAlternativo,
       ISNULL(CASE WHEN DAY(ALU_FechaNacimiento) > DAY(GETDATE())
            THEN (CAST(DATEDIFF(MONTH,ALU_FechaNacimiento,GETDATE())/12 AS nvarchar(MAX)) + ' años' + CASE WHEN (DATEDIFF(MONTH,ALU_FechaNacimiento,GETDATE())%12 - 1) = 0 THEN '' ELSE ', ' + CAST((DATEDIFF(MONTH,ALU_FechaNacimiento,GETDATE())%12 - 1) AS nvarchar(MAX)) + ' meses' END)
            ELSE (CAST(DATEDIFF(MONTH,ALU_FechaNacimiento,GETDATE())/12 AS nvarchar(MAX)) + ' años' + CASE WHEN DATEDIFF(MONTH,ALU_FechaNacimiento,GETDATE())%12 = 0 THEN '' ELSE ', ' + CAST(DATEDIFF(MONTH,ALU_FechaNacimiento,GETDATE())%12 AS nvarchar(MAX)) + ' meses' END)
       END, '') AS edad,
	   ALU_CorreoElectronico AS correoElectronico,
	   COALESCE(ALU_TelefonoMovil, ALU_TelefonoFijo, ALU_TelefonoTrabajo+COALESCE(' ('+ALU_TelefonoTrabajoExtension+')', ''), ALU_TelefonoMensajeriaInstantanea) AS telefono,
       SUC_Nombre AS sucursalNombre,
       ALU_Activo AS activo,
       ALU_Referencia AS referencia
FROM Alumnos
     INNER JOIN Sucursales ON SUC_SucursalId = ALU_SUC_SucursalId
GO