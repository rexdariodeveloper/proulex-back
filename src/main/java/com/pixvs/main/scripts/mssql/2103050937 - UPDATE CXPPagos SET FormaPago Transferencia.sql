UPDATE
	CXPPagos SET CXPP_FP_FormaPagoId = (select FP_FormaPagoId from FormasPago where FP_Nombre like N'Transferencia electrónica de fondos')
WHERE
	CXPP_FP_FormaPagoId IS NULL;