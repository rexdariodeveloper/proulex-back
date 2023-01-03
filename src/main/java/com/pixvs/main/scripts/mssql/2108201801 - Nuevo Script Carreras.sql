DECLARE @referencia varchar(255),@referenciaId INT,@nombre varchar(255)

SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUAAD')

SET @nombre = 'Maestria En Diseño De Informacion Y Comunicacion Digital'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Educacion Y Expresion Para Las Artes'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Estudios Cinematograficos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Etnomusicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Literacidad'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Urbanismo Y Territorio'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura  En Arquitectura'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Audiovisuales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Escenicas Para La Expresion Dancistica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Escenicas Para La Expresion Dancistica Nivelacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Escenicas Para La Expresion Teatral'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Escenicas Para La Expresion Teatral Nivelacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Visuales Para La Expresion Fotografica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Visuales Para La Expresion Plastica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Visuales Para La Expresion Plastica Nivelacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño Industrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño De Interiores  Y Ambientacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño De Modas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño Para La Comunicacion Grafica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño Arte Y Tecnologias Interactivas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Urbanistica Y Medio Ambiente'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUALTOS')

SET @nombre = N'Ingenieria Agroindustrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Cirujano Dentista'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Medicina Veterinaria Y Zootecnia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Negocios Internacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCBA')

SET @nombre = N'Especialidad En Produccion Porcina'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ciencia Del Comportamiento Orientacion En Analisis De La Conducta'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencia Del Comportamiento Orientacion En Neurociencia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Tecnologia De Semillas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingeniero Agronomo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura  En Biologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Agronegocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Ciencia De Los Alimentos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Medicina Veterinaria Y Zootecnia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCEA')

SET @nombre = N'Doctorado En Tecnologias De Informacion Con Orientacion En  Analisis De Sistemas Diseño Y Simulacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Administracion De Negocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ciencia De Los Datos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Direccion De Mercadotecnia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Estudios Fiscales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Finanzas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Gestion Y Politicas De La Educacion Superior Orientacion En Politicas De La Educacion Superior'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Innovacion Social Y Gestion Del Bienestar'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Negocios Y Estudios Economicos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Resolucion De Conflictos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Tecnologias De Informacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Tecnologias Para El Aprendizaje Con Orientacion En Docencia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Innovación Social Y Gestión Del Bienestar'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Negocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion Financiera Y Sistemas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion Gubernamental Y Politicas Publicas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Economia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Gestion De Negocios Gastronomicos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Gestion Y Economia Ambiental'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Mercadotecnia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Negocios Internacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Recursos Humanos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Relaciones Publicas Y Comunicacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Tecnologias De La Informacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Turismo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario
---------------------------------------------------------------------------------------------------------------------------------------
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCEI')

SET @nombre = N'Doctorado En Ciencia De Materiales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias De La Electronica Y La Computacion Con Orientaciones'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias En Fisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias En Ingenieria Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias En Microbiologia Y La Biotecnologia Molecular'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias En Procesos Biotecnologicos ( Tradicional )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias En Procesos Biotecnologicos (Directo)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Doctorado En Ciencias En Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencia De Materiales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencia De Productos Forestales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Bioingenieria Y Computo Inteligente'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Fisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Hidrometeorologia Con Orientacion En Oceanografia Y Meteorologia Fisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Ingenieria Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Inocuidad Alimentaria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Matematicas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Ciencias En Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Computo Aplicado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Biomedica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Civil'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Computacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Comunicaciones Y Electronica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Logistica Y Transporte'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Topografia Geomatica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Fotonica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Industrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Informatica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Mecanica Electrica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Robotica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Ciencia De Materiales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Fisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Ingenieria En Alimentos Y Biotecnologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Matematicas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Quimico Farmaceutico Biologo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

------------------------------------------------------------------------------------------------------------------------------------
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCIENEGA')

SET @nombre = N'Doctorado En Ciencia Politica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Administracion De Negocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ciencias Con Orientacion En Ciencias Biologicas Y Agropecuarias'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ciencias Con Orientacion En Ciencias Exactas  E Ingenierias'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Derecho'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Computacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Industrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Informatica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Quimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Agrobiotecnologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Agronegocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria (Nivelacion)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Mercadotecnia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Negocios Internacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Quimico Farmaceutico Biologo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Recursos Humanos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

------------------------------------------------*********************************************************************************

SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCOSTA')

SET @nombre = N'Maestria En Ciencias En Geofisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Civil'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Computacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Telematica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Videojuegos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura  En Arquitectura'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura  En Biologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Visuales Para La Expresion Fotografica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes Visuales Para La Expresion Plastica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ciencias Y Artes Culinarias'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Cultura Fisica Y Deportes'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño Para La Comunicacion Grafica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ingenieria En Comunicacion Multimedia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Turismo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

----------------------------------------************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCS')

SET @nombre = N'Doctorado En Ciencias Biomedicas Con Orientaciones En Inmunologia Y En Neurociencias'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Ciencias De La Nutricion Traslacional'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Ciencias En Biologia Molecular En Medicina'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Farmacologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Genetica Humana'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Especialidad De Enfermeria En Salud Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Especialidad En Endodoncia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ciencias De La Salud De La Adolescencia Y La Juventud'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Gerontologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Microbiologia Medica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Neuropsicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Nutricion Humana Orientacion Materno Infantil'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Psicologia De La Salud'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Psicologia Educativa'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Carrera De Enfermeria (Semiescolarizada)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Carrera En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ciencias Forenses'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Cirujano Dentista'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Cultura Fisica Y Deportes'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria (Modalidad A Distancia)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria (Nivelacion)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Podologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Tecnico Superior Universitario En Emergencias Seguridad Laboral Y Rescates'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Tecnico Superior Universitario En Protesis Dental'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Tecnico Superior Universitario En Radiologia E Imagen'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Tecnico Superior Universitario En Terapia Fisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Tecnico Superior Universitario En Terapia Respiratoria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

------------------------------------**************************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCSH')


SET @nombre = N'Doctorado En Geografia Y Ordenacion Territorial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Derecho'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Desarrollo Local Y Territorio'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Estudios De Las Lenguas Y Culturas Inglesas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Estudios Filosoficos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado ( Semiescolarizado )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Antropologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Comunicacion Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Criminologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Estudios Politicos Y Gobierno'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Filosofia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Geografia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Historia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Letras Hispanicas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Relaciones Internacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Sociologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Trabajo Social ( Escolarizada )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Trabajo Social (Nivelacion)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Escritura Creativa'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

----------------------------------------------------------------------*****************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUCSUR')

SET @nombre = N'Maestria En Administracion Y Gestion Regional Con Orientacion En Pequeñas Y Medianas Empresas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ciencias En Manejo De Recursos Naturales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria De Procesos Y Comercio Internacional'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Mecatronica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingeniero Agronomo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion Financiera Y Sistemas'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Artes ( Escolarizada )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Biologia Marina'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ingenieria En Obras Y Servicios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ingenieria En Recursos Naturales Y Agropecuarios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ingenieria En Teleinformatica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Turismo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Tecnico Superior Universitario En Electronica Y Mecanica Automotriz'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario
--------------------------------------------**********************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CULAGOS')

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado ( Semiescolarizado )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingeneria Bioquimica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Industrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Mecatronica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ingenieria En Administracion Industrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Lenguas Y Culturas Extranjeras'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Periodismo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario
--------------------------------***************************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUNORTE')

SET @nombre = N'Maestria En Administracion De Negocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Derecho'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Salud Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Electronica Y Computacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Mecanica Electrica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Agronegocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Antropologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Educacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria (Nivelacion)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

-----------------------------------------***********************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUSUR')

SET @nombre = N'Doctorado En Ciencia Del Comportamiento  Con Orientacion En Alimentacion Y Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Psicologia Con Orientacion En Calidad De Vida Y Salud'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Derecho'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Psicologia Con Orientacion En Calidad De Vida Y Salud'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Carrera En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Curso Posbasico De Enfermeria En Cuidados Intensivos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Curso Posbasico En Administracion Y Docencia En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Curso Posbasico En Enfermeria Medico Quirurgica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Telematica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Agrobiotecnologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Agronegocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Cirujano Dentista'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Cultura Fisica Y Deportes'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Desarrollo Turistico Sustentable'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria (Nivelacion)'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Medicina Veterinaria Y Zootecnia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Negocios Internacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Seguridad Laboral Proteccion Civil Y Emergencias'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Trabajo Social ( Mixta )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

------------------------------------*********************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUTLAJOMULCO')

SET @nombre = N'Licenciatura En Terapia Fisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion Y Gestion Empresarial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Mecatronica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Diseño Industrial'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria En Biotecnologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Civil'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Ingenieria Biomedica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Negocios Internacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

------------------------------------*********************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUTONALA')

SET @nombre = N'Doctorado En Agua Y Energia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Investigacion Multidisciplinaria En Salud'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ingenieria Del Agua Y La Energia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Ciencias Computacionales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Energia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Nanotecnologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Arquitectura'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion De Negocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Ciencias Forenses'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Diseño De Artesania'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Estudios Liberales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Gerontologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Historia Del Arte'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Salud Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Medico Cirujano Y Partero'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

------------------------------------*********************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'CUVALLES')

SET @nombre = N'Doctorado En Ciencias Fisico Matematicas Con Orientacion En Nanociencias'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Doctorado En Ciencias Fisico Matematicas Con Orientacion En Procesamiento Digital De Señales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ingenieria De Software'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Ingenieria Mecatronica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Tecnologias Para El Aprendizaje Con Orientacion En Gestion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Abogado'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Diseño Molecular De Materiales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Electronica Y Computacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Geofisica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Instrumentacion Electronica Y Nanosensores'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria En Sistemas Biologicos'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Ingenieria Mecatronica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Agronegocios'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Contaduria Publica'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Educacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Enfermeria'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Nutricion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Psicologia'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Tecnologias De La Informacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Trabajo Social ( Escolarizada )'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Turismo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


------------------------------------*********************************************************************************************
SET @referenciaId = (Select CMM_ControlId from ControlesMaestrosMultiples WHERE CMM_Referencia = 'SUV')

SET @nombre = N'Maestria En Desarrollo Y Direccion De La Innovacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario


SET @nombre = N'Maestria En Gestion Del Aprendizaje En Ambientes Virtuales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Gobierno Electronico'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Transparencia Y Proteccion De Datos Personales'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Maestria En Valuacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura De Mercadotecnia Digital'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Administracion De Las Organizaciones'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Bibliotecologia Y Gestion Del Conocimiento'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Desarrollo De Sistemas Web'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Desarrollo Educativo'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Gestion Cultural'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Periodismo Digital'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Seguridad Ciudadana'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario

SET @nombre = N'Licenciatura En Tecnologias E Informacion'
SET @referencia = (SELECT dbo.fnFirsties(@nombre))
EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_Carreras' --control
  ,@nombre-- valor
  ,@referencia -- referencia
  ,@referenciaId --referenciaId
  ,1 --usuario
GO