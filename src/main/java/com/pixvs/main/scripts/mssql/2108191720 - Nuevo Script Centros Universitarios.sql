DELETE from ControlesMaestrosMultiples
WHERE CMM_Control='CMM_ALU_Carreras' and CMM_ControlId >2000000
GO

DELETE from ControlesMaestrosMultiples
WHERE CMM_Control='CMM_ALU_CentrosUniversitarios' and CMM_ControlId >2000000
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario De Arte, Arquitectura y Diseño'-- valor
  ,N'CUAAD' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario De Los Altos'-- valor
  ,N'CUALTOS' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario De Ciencias Biológicas y Agropecuarias'-- valor
  ,N'CUCBA' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de Ciencias Económico Administrativas'-- valor
  ,N'CUCEA' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de Ciencias Exactas e Ingenierías'-- valor
  ,N'CUCEI' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de la Ciénega'-- valor
  ,N'CUCIENEGA' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de la Costa'-- valor
  ,N'CUCOSTA' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de Ciencias de la Salud'-- valor
  ,N'CUCS' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de Ciencias Sociales y Humanidades'-- valor
  ,N'CUCSH' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario del Sur'-- valor
  ,N'CUSUR' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de los Lagos'-- valor
  ,N'CULAGOS' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario del Norte'-- valor
  ,N'CUNORTE' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de Tlajomulco'-- valor
  ,N'CUTLAJOMULCO' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de Tonalá'-- valor
  ,N'CUTONALA' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Centro Universitario de los Valles'-- valor
  ,N'CUVALLES' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO

EXECUTE [dbo].[sp_InsertCMM] 
   N'CMM_ALU_CentrosUniversitarios' --control
  ,N'Sistema de Universidad Virtual'-- valor
  ,N'SUV' -- referencia
  ,NULL --referenciaId
  ,1 --usuario
GO