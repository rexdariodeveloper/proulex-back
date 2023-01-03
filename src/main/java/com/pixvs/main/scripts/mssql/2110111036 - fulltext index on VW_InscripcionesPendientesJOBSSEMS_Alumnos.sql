/**
* Created by Angel Daniel Hernández Silva on 11/10/2021.
* Object: fulltext index on VW_InscripcionesPendientesJOBSSEMS_Alumnos
*/

create unique clustered index id on dbo.VW_InscripcionesPendientesJOBSSEMS_Alumnos (id)
GO
​
create fulltext index on VW_InscripcionesPendientesJOBSSEMS_Alumnos(textoBusqueda)
key index id
GO