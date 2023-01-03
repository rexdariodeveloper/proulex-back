/**
 * Created by Angel Daniel Hernández Silva on 21/06/2021.
 * Object:  VIEW [dbo].[VW_REPORTE_EXCEL_ALUMNOS]
 */


CREATE OR ALTER VIEW [dbo].[VW_REPORTE_EXCEL_ALUMNOS] AS

    SELECT
        ALU_Codigo AS "Código alumno",
        ALU_Nombre AS "Nombre",
        ALU_PrimerApellido + COALESCE(' ' + ALU_SegundoApellido,'') AS "Apellidos",
        CAST(DATEDIFF(YEAR,ALU_FechaNacimiento,GETDATE()) AS nvarchar(MAX)) + ' años, ' + CAST(DATEDIFF(MONTH,ALU_FechaNacimiento,DATEADD(YEAR,DATEDIFF(YEAR,GETDATE(),ALU_FechaNacimiento),GETDATE())) AS nvarchar(MAX)) + ' meses' AS "Edad",
		ALU_CorreoElectronico AS "Correo electrónico",
		COALESCE(ALU_TelefonoMovil,ALU_TelefonoFijo,ALU_TelefonoTrabajo + COALESCE(' (' + ALU_TelefonoTrabajoExtension + ')',''), ALU_TelefonoMensajeriaInstantanea) AS "Teléfono",
		'' AS "Sede",
		CASE WHEN ALU_Activo = 1 THEN 'Activo' ELSE 'Inactivo' END AS "Estatus"
    FROM Alumnos

GO