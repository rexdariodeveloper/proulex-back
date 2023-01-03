UPDATE MenuListadosGenerales SET MLG_NodoPadreId = (select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo = 'Generales')
where MLG_Titulo IN ('Genero','Estado Civil','Nacionalidad','Tipo de sangre','Grado de estudios')

GO

UPDATE MenuListadosGenerales SET MLG_Titulo = 'Escolaridad'
where MLG_Titulo = 'Grado de estudios'

GO
 
UPDATE MenuListadosGenerales SET MLG_NodoPadreId = (select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo = 'Generales')
where MLG_NodoPadreId = (select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo = 'CONTROL ESCOLAR')
and  MLG_Titulo = 'Parentesco'

GO

