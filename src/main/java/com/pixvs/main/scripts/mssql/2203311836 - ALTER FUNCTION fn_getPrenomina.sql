/**
 * Created by Angel Daniel Hernández Silva on 31/03/2022.
 * Object: ALTER FUNCTION [dbo].[fn_getPrenomina]
 */
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fn_getPrenomina] (@fechaInicioQuincena Date, @fechaFinQuincena Date)
RETURNS @tablaTMP table(
	idEmpleado int,
	codigoEmpleado varchar(80),
	idSucursal int,
	sucursal varchar(250),
	idGrupo int,
	codigoGrupo varchar(80),
	empleado varchar(250),
	deduccionPercepcionId int,
	tabulador varchar(80),
	nombreGrupo varchar(250),
	horasPagadas decimal(5,2),
	percepcion varchar(250),
	deduccion varchar(250),
	idioma varchar(250),
	fechaDiaFestivo Date,
	fechaDiaSuplida Date,
	categoria varchar(10),
	sueldoPorHora varchar(250),
	idSuplencia int,
	fechaInicioPeriodo date,
	fechaFinPeriodo date,
	sueldoPorHoraDecimal decimal(10,2),
	tipoMovimientoId int,
	movimientoReferenciaId int,
	referenciaProcesoTabla varchar(100),
	referenciaProcesoId int
)

AS BEGIN
	INSERT @tablaTMP
	SELECT *
	FROM(

		/***********************************/
		/***** Clases profesor titular *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvv*****/
		
		SELECT * FROM [dbo].[fn_getPrenominaProfesorTitular] (@fechaInicioQuincena,@fechaFinQuincena)

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Clases profesor titular *****/
		/***********************************/

		UNION ALL

		/****************************/
		/***** Clases suplencia *****/
		/*****vvvvvvvvvvvvvvvvvv*****/

		SELECT * FROM [dbo].[fn_getPrenominaProfesorSuplente] (@fechaInicioQuincena,@fechaFinQuincena)

		/*****^^^^^^^^^^^^^^^^^^*****/
		/***** Clases suplencia *****/
		/****************************/

		UNION ALL

		/**************************************/
		/***** Deducciones y percepciones *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT * FROM [dbo].[fn_getPrenominaDeduccionesPercepciones] (@fechaInicioQuincena,@fechaFinQuincena)

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducciones y percepciones *****/
		/**************************************/

		UNION ALL

		/*************************/
		/***** Días festivos *****/
		/*****vvvvvvvvvvvvvvv*****/

		SELECT * FROM [dbo].[fn_getPrenominaDiasFestivos] (@fechaInicioQuincena,@fechaFinQuincena)

		/*****^^^^^^^^^^^^^^^*****/
		/***** Días festivos *****/
		/*************************/

		UNION ALL

		/************************************/
		/***** Deducción días suplencia *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvv*****/
		
        SELECT * FROM [dbo].[fn_getPrenominaDeduccionesDiasSuplencia] (@fechaInicioQuincena,@fechaFinQuincena)

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducción días suplencia *****/
		/************************************/

		UNION ALL

		/**************************************************/
		/***** Deducciones cambio de profesor titular *****/
		/*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*****/

		SELECT * FROM [dbo].[fn_getPrenominaDeduccionesCambioProfesorTitular] (@fechaInicioQuincena,@fechaFinQuincena)

		/*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*****/
		/***** Deducciones cambio de profesor titular *****/
		/**************************************************/
	) Q4

	ORDER BY idEmpleado,codigoGrupo ASC


RETURN;
END