DROP TABLE IF EXISTS EmpresasPermisosComponentes
GO
DROP TABLE IF EXISTS Empresas
GO

-- Creamos la tabla Empresas
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE Empresas (
	EM_EmpresaId INT IDENTITY(1,1) NOT NULL,	
	EM_Codigo VARCHAR(20) NOT NULL,
	EM_Nombre VARCHAR(150) NOT NULL
 CONSTRAINT PK_Empresas PRIMARY KEY CLUSTERED 
(
	EM_EmpresaId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Creamos la tabla para los permisos
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE EmpresasPermisosComponentes (
	EMPC_EmpresaPermisoComponenteId INT IDENTITY(1,1) NOT NULL,	
	EMPC_EM_EmpresaId INT NOT NULL, 
	EMPC_MP_NodoId INT NOT NULL, 
	EMPC_CMM_TipoPermisoId INT NOT NULL, 
	EMPC_Componente VARCHAR (150) NOT NULL
 CONSTRAINT PK_EmpresasPermisosComponentes PRIMARY KEY CLUSTERED 
(
	EMPC_EmpresaPermisoComponenteId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE EmpresasPermisosComponentes  WITH CHECK ADD  CONSTRAINT FK_EmpresasPermisosComponentes_Empresa FOREIGN KEY(EMPC_EM_EmpresaId)
REFERENCES Empresas (EM_EmpresaId)
GO
ALTER TABLE EmpresasPermisosComponentes CHECK CONSTRAINT FK_EmpresasPermisosComponentes_Empresa
GO

ALTER TABLE EmpresasPermisosComponentes  WITH CHECK ADD  CONSTRAINT FK_EmpresasPermisosComponentes_Nodo FOREIGN KEY(EMPC_MP_NodoId)
REFERENCES MenuPrincipal (MP_NodoId)
GO
ALTER TABLE EmpresasPermisosComponentes CHECK CONSTRAINT FK_EmpresasPermisosComponentes_Nodo
GO

ALTER TABLE EmpresasPermisosComponentes  WITH CHECK ADD  CONSTRAINT FK_EmpresasPermisosComponentes_TipoPermiso FOREIGN KEY(EMPC_CMM_TipoPermisoId)
REFERENCES ControlesMaestrosMultiples (CMM_ControlId)
GO
ALTER TABLE EmpresasPermisosComponentes CHECK CONSTRAINT FK_EmpresasPermisosComponentes_TipoPermiso
GO

-- Registro de empresa PIXVS
SET IDENTITY_INSERT Empresas ON
GO
INSERT INTO Empresas
(
    EM_EmpresaId, -- this column value is auto-generated
    EM_Codigo,
    EM_Nombre
)
VALUES
(
    1, -- EM_EmpresaId - int
    'PIXVS', -- EM_Codigo - varchar
    'DESARROLLADORA DE SOFTWARE EMPRESARIAL Y DE NEGOCIOS' -- EM_Nombre - varchar
)
GO
SET IDENTITY_INSERT Empresas OFF
GO