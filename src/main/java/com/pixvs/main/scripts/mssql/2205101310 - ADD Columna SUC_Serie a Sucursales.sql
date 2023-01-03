ALTER TABLE Sucursales ADD SUC_Serie VARCHAR(4) NULL
GO

UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'AAD'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'ALA'
UPDATE Sucursales SET SUC_Serie = 'PS' WHERE SUC_CodigoSucursal = 'ALC'
UPDATE Sucursales SET SUC_Serie = 'PK' WHERE SUC_CodigoSucursal = 'AUT'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'CBA'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'CCI'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'CEA'
UPDATE Sucursales SET SUC_Serie = 'PU' WHERE SUC_CodigoSucursal = 'CEI'
UPDATE Sucursales SET SUC_Serie = 'PO' WHERE SUC_CodigoSucursal = 'CIP'
UPDATE Sucursales SET SUC_Serie = 'PB' WHERE SUC_CodigoSucursal = 'COM'
UPDATE Sucursales SET SUC_Serie = 'PA' WHERE SUC_CodigoSucursal = 'COR'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'CSH'
UPDATE Sucursales SET SUC_Serie = 'PF' WHERE SUC_CodigoSucursal = 'CUC'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'CUR'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'EGA'
UPDATE Sucursales SET SUC_Serie = 'PZ' WHERE SUC_CodigoSucursal = 'FID'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'GOS'
UPDATE Sucursales SET SUC_Serie = 'PX' WHERE SUC_CodigoSucursal = 'INT'
UPDATE Sucursales SET SUC_Serie = 'PN' WHERE SUC_CodigoSucursal = 'LAG'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'LES'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'NTE'
UPDATE Sucursales SET SUC_Serie = 'PL' WHERE SUC_CodigoSucursal = 'OCO'
UPDATE Sucursales SET SUC_Serie = 'PY' WHERE SUC_CodigoSucursal = 'PIP'
UPDATE Sucursales SET SUC_Serie = 'PI' WHERE SUC_CodigoSucursal = 'PTO'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'RUS'
UPDATE Sucursales SET SUC_Serie = 'PT' WHERE SUC_CodigoSucursal = 'SAA'
UPDATE Sucursales SET SUC_Serie = 'PP' WHERE SUC_CodigoSucursal = 'SCM'
UPDATE Sucursales SET SUC_Serie = 'PR' WHERE SUC_CodigoSucursal = 'SID'
UPDATE Sucursales SET SUC_Serie = 'PC' WHERE SUC_CodigoSucursal = 'SOC'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'STA'
UPDATE Sucursales SET SUC_Serie = 'PQ' WHERE SUC_CodigoSucursal = 'SUR'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'SUV'
UPDATE Sucursales SET SUC_Serie = 'PE' WHERE SUC_CodigoSucursal = 'TEC'
UPDATE Sucursales SET SUC_Serie = 'PM' WHERE SUC_CodigoSucursal = 'TEP'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'TOS'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'UCS'
UPDATE Sucursales SET SUC_Serie = 'PG' WHERE SUC_CodigoSucursal = 'UNI'
UPDATE Sucursales SET SUC_Serie = 'PV' WHERE SUC_CodigoSucursal = 'UTG'
UPDATE Sucursales SET SUC_Serie = 'PD' WHERE SUC_CodigoSucursal = 'VAL'
UPDATE Sucursales SET SUC_Serie = 'PH' WHERE SUC_CodigoSucursal = 'YAC'
UPDATE Sucursales SET SUC_Serie = 'PJ' WHERE SUC_CodigoSucursal = 'ZAP'

UPDATE Sucursales SET SUC_Serie = 'PX' WHERE SUC_CodigoSucursal = 'JBS'
UPDATE Sucursales SET SUC_Serie = 'PW' WHERE SUC_CodigoSucursal = 'JOB'
UPDATE Sucursales SET SUC_Serie = 'PA' WHERE SUC_CodigoSucursal = 'MAT'

UPDATE Sucursales SET SUC_Serie = '' WHERE SUC_Serie IS NULL
GO

ALTER TABLE Sucursales ALTER COLUMN SUC_Serie VARCHAR(4) NOT NULL
GO