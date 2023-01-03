ALTER TABLE EntidadesBecas DROP COLUMN ENBE_PrecioAnual
GO

ALTER TABLE EntidadesBecas ADD ENBE_LIPRE_ListadoPrecioId INT
GO

ALTER TABLE EntidadesBecas  WITH CHECK ADD  CONSTRAINT FK_EntidadesBecas_ListadoPrecios FOREIGN KEY(ENBE_LIPRE_ListadoPrecioId)
REFERENCES ListadosPrecios (LIPRE_ListadoPrecioId)
GO
ALTER TABLE EntidadesBecas CHECK CONSTRAINT FK_EntidadesBecas_ListadoPrecios
GO

UPDATE MenuListadosGeneralesDetalles
  SET
      MLGD_CampoTabla = 'ENBE_LIPRE_ListadoPrecioId',
      MLGD_CampoModelo = 'listadoPrecio',
      MLGD_JsonConfig = '{' + 
	  '"type" : "pixvsMatSelect",' + 
	  '"label" : "Listado Precios",' + 
	  '"name" : "listadoPrecio",' + 
	  '"formControl" : "new FormControl()",' + 
	  '"validations":[],' + 
	  '"multiple" : false,' + 
	  '"selectAll" : false,' + 
	  '"list":[],' + 
	  '"campoValor" : "nombre",' +
	  '"fxFlex" : "100"' + 
	  '}',
      MLGD_JsonListado = '{' + 
	  '"name" : "listadoPrecio.nombre",' + 
	  '"title" : "Listado Precios",' + 
	  '"class" : "",' + 
	  '"centrado" : false,' + 
	  '"type" : null,' + 
	  '"tooltip" : false' + 
	  '}'
WHERE MLGD_MLG_ListadoGeneralNodoId = 55
      AND MLGD_CampoTabla = 'ENBE_PrecioAnual'
GO

UPDATE MenuListadosGeneralesDetalles
  SET
      MLGD_JsonConfig = REPLACE(MLGD_JsonConfig, '?', 'ó'),
      MLGD_JsonListado = REPLACE(MLGD_JsonListado, '?', 'ó')
WHERE MLGD_MLG_ListadoGeneralNodoId = 55
GO