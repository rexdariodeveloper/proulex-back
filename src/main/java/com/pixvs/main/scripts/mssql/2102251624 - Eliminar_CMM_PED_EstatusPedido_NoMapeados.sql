/*
 * Benjamin Osorio
 * ELiminar los registros CMM_PED_EstatusPedido que no se encuentran mapeados, ya que estan duplicados
*/
DELETE FROM ControlesMaestrosMultiples
WHERE
    CMM_Control = 'CMM_PED_EstatusPedido'
  AND CMM_ControlId BETWEEN 2000080 AND 2000089