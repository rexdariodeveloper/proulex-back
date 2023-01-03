/**
 * Created by Angel Daniel Hernández Silva on 05/11/2021.
 * Object:  Table [dbo].[ControlesMaestrosMultiplesResponsables]
 */

CREATE TABLE [dbo].[ControlesMaestrosMultiplesResponsables](
    [CUR_CMM_ControlId] [int] NOT NULL,
    [CUR_USU_ResponsableId] [int] NOT NULL,
    CONSTRAINT [PK_ControlesMaestrosMultiplesResponsables] PRIMARY KEY CLUSTERED (
        [CUR_CMM_ControlId] ASC,
        [CUR_USU_ResponsableId] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[ControlesMaestrosMultiplesResponsables]  WITH CHECK ADD  CONSTRAINT [FK_CUR_CMM_ControlId] FOREIGN KEY([CUR_CMM_ControlId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiplesResponsables] CHECK CONSTRAINT [FK_CUR_CMM_ControlId]
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiplesResponsables]  WITH CHECK ADD  CONSTRAINT [FK_CUR_USU_ResponsableId] FOREIGN KEY([CUR_USU_ResponsableId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiplesResponsables] CHECK CONSTRAINT [FK_CUR_USU_ResponsableId]
GO

INSERT [dbo].[ControlesMaestrosMultiplesResponsables]([CUR_CMM_ControlId],[CUR_USU_ResponsableId]) VALUES
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PJA'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'lara_neri@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P02'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'martin.villalobos@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P03'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'reginaldo.gonzalez@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P04'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'herminia.flores@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P05'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'fperez@redudg.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P06'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = ' ramon.delacruz@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P07'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'irene.gomez@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P08'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'hector.pelayo@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMQ'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'hector.pelayo@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P09'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'juan.solorzano@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P10'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'javier.castro@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P11'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'claudia.luquin@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P12'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'maricela.diazm@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMT'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'maricela.diazm@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P13'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'delgadillo.zarate@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P14'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'juan.rodriguezb@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PME'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'juan.rodriguezb@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P15'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'jmpadilla@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P16'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'miguel.campos@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P17'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'ricardo.lomeli@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P18'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'jorges@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P19'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'juan.rodriguezb@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P20'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'led.cardenas@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'P22'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'sofia.valerio@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PTO'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'ludwig.rosas@redudg.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PTN'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'Karina.Cerrillos@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PRL'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'guillermo.mejia@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMC'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'guillermo.mejia@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMZ'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'guillermo.mejia@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMA'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'guillermo.mejia@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PRA'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'evelia.moreno@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PRT'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'rafael.barrera@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PRC'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'vanessa.huizar@redudg.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMM'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'vanessa.huizar@redudg.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMS'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'vanessa.huizar@redudg.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PRS'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'dante.moreno@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMJ'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'dante.moreno@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PVO'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'Raul.Gonzalez@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PSV'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'saviles@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PPG'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'gisely@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PPM'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'teresa.godinez@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PRH'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'daniela.rentería@sems.udg.mx')),
((SELECT CMM_ControlId FROM ControlesMaestrosMultiples WHERE CMM_Control = 'CMM_ALU_Preparatorias' AND CMM_Referencia = 'PMX'), (SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'daniela.rentería@sems.udg.mx'))
GO