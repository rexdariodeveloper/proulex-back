DROP TABLE IF EXISTS SATPeriodicidad
GO
DROP TABLE IF EXISTS SATMeses
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE SATPeriodicidad(
	PER_PeriodicidadId INT IDENTITY(1,1) NOT NULL,	
	PER_Codigo NVARCHAR(5) NOT NULL,
	PER_Descripcion NVARCHAR(250) NOT NULL,
	PER_Activo BIT NOT NULL
 CONSTRAINT PK_SATPeriodicidad PRIMARY KEY CLUSTERED 
(
	PER_PeriodicidadId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE SATMeses(
	MES_MesId INT IDENTITY(1,1) NOT NULL,	
	MES_Codigo NVARCHAR(5) NOT NULL,
	MES_Descripcion NVARCHAR(250) NOT NULL,
	MES_PER_PeriodicidadId INT NULL,
	MES_Activo BIT NOT NULL
 CONSTRAINT PK_SATMeses PRIMARY KEY CLUSTERED 
(
	MES_MesId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

INSERT INTO SATPeriodicidad
(
    --PER_PeriodicidadId - this column value is auto-generated
    PER_Codigo,
    PER_Descripcion,
    PER_Activo
)
VALUES
('01', 'Diario', 1),
('02', 'Semanal', 1),
('03', 'Quincenal', 1),
('04', 'Mensual', 1),
('05', 'Bimestral', 1)
GO

INSERT INTO SATMeses
(
    --MES_MesId - this column value is auto-generated
    MES_Codigo,
    MES_Descripcion,
    MES_PER_PeriodicidadId,
    MES_Activo
)
VALUES
('01', 'Enero', NULL,1),
('02', 'Febrero', NULL,1),
('03', 'Marzo', NULL,1),
('04', 'Abril', NULL,1),
('05', 'Mayo', NULL,1),
('06', 'Junio', NULL,1),
('07', 'Julio', NULL,1),
('08', 'Agosto', NULL,1),
('09', 'Septiembre', NULL,1),
('10', 'Octubre', NULL,1),
('11', 'Noviembre', NULL,1),
('12', 'Diciembre', NULL,1),
('13', 'Enero-Febrero', (SELECT PER_PeriodicidadId FROM SATPeriodicidad WHERE PER_Codigo = '05' AND PER_Activo = 1),1),
('14', 'Marzo-Abril', (SELECT PER_PeriodicidadId FROM SATPeriodicidad WHERE PER_Codigo = '05' AND PER_Activo = 1),1),
('15', 'Mayo-Junio', (SELECT PER_PeriodicidadId FROM SATPeriodicidad WHERE PER_Codigo = '05' AND PER_Activo = 1),1),
('16', 'Julio-Agosto', (SELECT PER_PeriodicidadId FROM SATPeriodicidad WHERE PER_Codigo = '05' AND PER_Activo = 1),1),
('17', 'Septiembre-Octubre', (SELECT PER_PeriodicidadId FROM SATPeriodicidad WHERE PER_Codigo = '05' AND PER_Activo = 1),1),
('18', 'Noviembre-Diciembre', (SELECT PER_PeriodicidadId FROM SATPeriodicidad WHERE PER_Codigo = '05' AND PER_Activo = 1),1)
GO