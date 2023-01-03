SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_getListadoEmpleados]
AS
    DECLARE
        @cols  nvarchar(max),
        @stmt  nvarchar(max)
    SET @cols = (SELECT STRING_AGG(CMM_Valor,',') FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Activo = 1)

    SELECT @stmt = '
        SELECT

            Emp.EMP_CodigoEmpleado "codigo Empleado",
            CONCAT(COALESCE(Usu.USU_Nombre,Emp.EMP_Nombre),'' '',COALESCE(Usu.USU_PrimerApellido,Emp.EMP_PrimerApellido),'' '',COALESCE(Usu.USU_SegundoApellido,Emp.EMP_SegundoApellido)) as nombre,
            Emp.EMP_FechaNacimiento AS fechaNacimiento,
            PaisN.PAI_Nombre AS paisNacimiento,
            EstN.EST_Nombre as estadoNacimiento,
            EstCiv.CMM_Valor AS estadoCivil,
            Gen.CMM_Valor as genero,
            Emp.EMP_RFC as rfc,
            Emp.EMP_CURP AS curp,
            Emp.EMP_CorreoElectronico AS correo,
            Emp.EMP_Activo AS activo,

            (Select Cmm.CMM_Valor from ControlesMaestrosMultiples Cmm where Cmm.CMM_ControlId = Emp.EMP_CMM_TipoEmpleadoId) as tipoEmpleado,
            Dep.DEP_Nombre as departamento,
            Emp.EMP_FechaAlta fechaAlta,
            tipoCont.CMM_Valor as tipoContrato,
            (Select Cmm.CMM_Valor from ControlesMaestrosMultiples Cmm where Cmm.CMM_ControlId = Emp.EMP_CMM_PuestoId) as puesto,
            Suc.SUC_Nombre as sucursal,

            Emp.EMP_Domicilio as domicilio,
            Emp.EMP_Colonia as colonia,
            Emp.EMP_CP as codigoPostal,
            PaisD.PAI_Nombre as pais,
            EstD.EST_Nombre as estado,
            Emp.EMP_Municipio as ciudad,
            Emp.EMP_TelefonoContacto as telefono,
            '+@cols+'
        FROM
            Empleados Emp
            INNER JOIN Departamentos Dep ON Dep.DEP_DepartamentoId = Emp.EMP_DEP_DepartamentoId
            INNER JOIN Sucursales Suc ON Suc.SUC_SucursalId = Emp.EMP_SUC_SucursalId
            LEFT JOIN Usuarios Usu ON Usu.USU_UsuarioId = Emp.EMP_USU_UsuarioId
            INNER JOIN Paises PaisN ON PaisN.PAI_PaisId = Emp.EMP_PAI_PaisNacimientoId
            INNER JOIN Estados EstN ON EstN.EST_EstadoId = Emp.EMP_EST_EstadoNacimientoId
            INNER JOIN ControlesMaestrosMultiples EstCiv ON EstCiv.CMM_ControlId = Emp.EMP_CMM_EstadoCivilId
            INNER JOIN ControlesMaestrosMultiples Gen ON Gen.CMM_ControlId = Emp.EMP_CMM_GeneroId
            INNER JOIN ControlesMaestrosMultiples tipoCont ON tipoCont.CMM_ControlId = Emp.EMP_CMM_TipoContratoId
            INNER JOIN Paises PaisD ON PaisD.PAI_PaisId = Emp.EMP_PAI_PaisId
            INNER JOIN Estados EstD ON EstD.EST_EstadoId = Emp.EMP_EST_EstadoId

            LEFT JOIN
                (
                    SELECT
                        EMPCA_EMP_EmpleadoId AS idEmpleado, idioma.CMM_Valor AS idioma, Cat.PAPC_Categoria AS categoria
                    FROM EmpleadosCategorias
                    INNER JOIN ControlesMaestrosMultiples idioma ON idioma.CMM_ControlId =  EMPCA_CMM_IdiomaId
                    INNER JOIN PAProfesoresCategorias Cat ON Cat.PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId
                    WHERE EMPCA_Activo = 1
                )  AS source_categorias
                PIVOT(
                MAX(categoria) FOR idioma IN('+@cols+')
                ) AS pvt_cat ON pvt_cat.idEmpleado = Emp.EMP_EmpleadoId

        WHERE
            EMP_Activo = 1'
    EXEC sp_executesql @stmt = @stmt
GO

