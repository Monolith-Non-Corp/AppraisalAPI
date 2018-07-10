USE [Appraisal]
GO
/****** Object:  Table [dbo].[Area_Proceso]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Area_Proceso](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](255) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
	[nivel] [int] NOT NULL,
	[categoria] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Artefacto]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Artefacto](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[archivo] [varbinary](max) NOT NULL,
	[nombre] [varchar](max) NOT NULL,
	[tipo] [varchar](255) NULL,
	[fecha] [datetime2](7) NOT NULL,
	[evidencia] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categoria]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categoria](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Evidencia]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Evidencia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[practica_especifica] [int] NOT NULL,
	[instancia] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hipervinculo]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hipervinculo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[link] [varchar](max) NULL,
	[evidencia] [int] NOT NULL,
	[fecha] [datetime2](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Instancia]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Instancia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](max) NOT NULL,
	[instancia_tipo] [int] NOT NULL,
	[organizacion] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Instancia_Tipo]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Instancia_Tipo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Meta_Especifica]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Meta_Especifica](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](255) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
	[area_proceso] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Nivel]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Nivel](
	[lvl] [int] NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[lvl] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Organizacion]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Organizacion](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](max) NOT NULL,
	[nivel] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Persona]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Persona](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](max) NOT NULL,
	[primer_apellido] [varchar](max) NULL,
	[segundo_apellido] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Practica_Especifica]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Practica_Especifica](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](255) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
	[meta_especifica] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](max) NOT NULL,
	[password] [varchar](max) NOT NULL,
	[persona] [int] NOT NULL,
	[rol] [int] NOT NULL,
	[organizacion] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario_Rol]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario_Rol](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Area_Proceso] ON 

INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (1, N'(PP)', N'Project Planning', 2, 1)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (2, N'(MA)', N'Measurement and Analysis', 2, 4)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (3, N'(REQM)', N'Requirements Management', 2, 1)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (4, N'(PMC)', N'Project Monitoring and Control', 2, 1)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (5, N'(CM)', N'Configuration Management', 2, 4)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (6, N'(PPQA)', N'Process and Product Quality Assurance', 2, 4)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (7, N'(SAM)', N'Supplier Agreement Management', 2, 2)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (8, N'(DAR)', N'Decision Analysis Resolution ', 3, 4)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (9, N'(IPM)', N'Integrated Project Management', 3, 1)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (10, N'(OPD)', N'Organizational Process Definition', 3, 2)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (11, N'(OPF)', N'Organizational Process Focus', 3, 2)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (12, N'(OT)', N'Organizational Training', 3, 2)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (13, N'(PI)', N'Product Integration', 3, 3)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (14, N'(RD)', N'Requirements Development', 3, 3)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (15, N'(RSKM)', N'Risk Management', 3, 1)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (16, N'(TS)', N'Technical Solution', 3, 3)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (17, N'(VAL)', N'Validation', 3, 3)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (18, N'(VER)', N'Verification', 3, 3)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (19, N'(QPM)', N'Quantitative Project Management', 4, 1)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (20, N'(OPP)', N'Organizational Process Performance', 4, 2)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (21, N'(OID)', N'Implementation and Organizational Development', 5, 2)
INSERT [dbo].[Area_Proceso] ([id], [nombre], [descripcion], [nivel], [categoria]) VALUES (22, N'(CAR)', N'Causal and Resolution Analysis', 5, 4)
SET IDENTITY_INSERT [dbo].[Area_Proceso] OFF
SET IDENTITY_INSERT [dbo].[Categoria] ON 

INSERT [dbo].[Categoria] ([id], [nombre]) VALUES (1, N'GESTIÓN DE PROYECTO')
INSERT [dbo].[Categoria] ([id], [nombre]) VALUES (2, N'GESTIÓN DE PROCESOS')
INSERT [dbo].[Categoria] ([id], [nombre]) VALUES (3, N'INGENIERÍA')
INSERT [dbo].[Categoria] ([id], [nombre]) VALUES (4, N'SOPORTE')
SET IDENTITY_INSERT [dbo].[Categoria] OFF
SET IDENTITY_INSERT [dbo].[Meta_Especifica] ON 

INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (1, N'SG 1', N'Establecer las estimaciones (Negociación)', 1)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (2, N'SG 2', N'Desarrollar un plan de proyecto', 1)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (3, N'SG 3', N'Obtener el compromiso con el plan', 1)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (4, N'SG 1', N'Alinear las actividades de medición y análisis', 2)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (5, N'SG 2', N'Proporcionar los resultados de la medición', 2)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (6, N'SG 1', N'Gestión de Requisitos', 3)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (7, N'SG 1', N'Monitorizar el proyecto frente al plan', 4)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (8, N'SG 2', N'Gestionar las acciones correctivas hasta su cierre', 4)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (9, N'SG 1', N'Establecer las líneas base', 5)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (10, N'SG 2', N'Seguir y controlar los cambios', 5)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (11, N'SG 3', N'Establecer la integridad', 5)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (12, N'SG 1', N'Evaluar objetivamente los procesos y los productos de trabajo', 6)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (13, N'SG 2', N'Proporcionar una visión objetiva', 6)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (14, N'SG 1', N'Establecer acuerdos con proveedores', 7)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (15, N'SG 2', N'Satisfacer los acuerdos con los proveedores', 7)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (16, N'SG 1', N'Evaluar las alternativas', 8)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (17, N'SG 1', N'Utilizar el proceso definido del proyecto', 9)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (18, N'SG 2', N'Coordinar y colaborar con las partes interesadas relevantes', 9)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (19, N'SG 1', N'Establecer los activos de procesos de la organización', 10)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (20, N'SG 1', N'Determinar las oportunidades de mejora de procesos', 11)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (21, N'SG 2', N'Planificar e implementar las acciones de proceso', 11)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (22, N'SG 3', N'Desglegar los activos de proceso de la organización e incorporar las experiencias', 11)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (23, N'SG 1', N'Establecer una capacidad de formación de la organización', 12)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (24, N'SG 2', N'Proporcionar formación', 12)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (25, N'SG 1', N'Prepararse para la integración del producto', 13)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (26, N'SG 2', N'Asegurar la compatibilidad de las interfaces', 13)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (27, N'SG 3', N'Ensamblar los componentes de producto y entregar el producto', 13)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (28, N'SG 1', N'Desarrollar los requisitos del cliente', 14)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (29, N'SG 2', N'Desarrollar los requisitos de producto', 14)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (30, N'SG 3', N'Analizar y validar los requisitos', 14)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (31, N'SG 1', N'Preparar la gestión de riesgos', 15)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (32, N'SG 2', N'Identificar y analizar los riesgos', 15)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (33, N'SG 3', N'Mitigar los riesgos', 15)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (34, N'SG 1', N'Seleccionar soluciones de componentes de producto', 16)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (35, N'SG 2', N'Desarrollar el diseño', 16)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (36, N'SG 3', N'Implementar el diseño del producto', 16)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (37, N'SG 1', N'Preparar la validación', 17)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (38, N'SG 2', N'Validar el producto o los componentes de producto', 17)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (39, N'SG 1', N'Preparar la verificación', 18)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (40, N'SG 2', N'Realizar las revisiones entre pares', 18)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (41, N'SG 3', N'Verificar los productos de trabajo seleccionados', 18)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (42, N'SG 1', N'Prepararse para la gestión cuantitativa', 19)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (43, N'SG 2', N'Gestionar el proyecto cuantitativamente', 19)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (44, N'SG 1', N'Establecer lineas base de rendimiento y modelos', 20)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (45, N'SG 1', N'Selecionar mejoras', 21)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (46, N'SG 2', N'Desplegar mejoras', 21)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (47, N'SG 1', N'Determinar causas de resultados seleccionadas', 22)
INSERT [dbo].[Meta_Especifica] ([id], [nombre], [descripcion], [area_proceso]) VALUES (48, N'SG 2', N'Discutir las causas de resultados seleccionados', 22)
SET IDENTITY_INSERT [dbo].[Meta_Especifica] OFF
INSERT [dbo].[Nivel] ([lvl], [descripcion]) VALUES (2, N'Nivel 2')
INSERT [dbo].[Nivel] ([lvl], [descripcion]) VALUES (3, N'Nivel 3')
INSERT [dbo].[Nivel] ([lvl], [descripcion]) VALUES (4, N'Nivel 4')
INSERT [dbo].[Nivel] ([lvl], [descripcion]) VALUES (5, N'Nivel 5')
SET IDENTITY_INSERT [dbo].[Persona] ON 

INSERT [dbo].[Persona] ([id], [nombre], [primer_apellido], [segundo_apellido]) VALUES (1, N'Ed', N'Gar', N'Cas')
SET IDENTITY_INSERT [dbo].[Persona] OFF
SET IDENTITY_INSERT [dbo].[Practica_Especifica] ON 

INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (1, N'SP 1.1', N'Estimar el alcance del proyecto', 1)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (2, N'SP 1.2', N'Establecer las estimaciones de los atributos de los productos de trabajo y de las tareas (atributos:  alcance esfuerzo, complejidad, tiempo, costo)', 1)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (3, N'SP 1.3', N'Definir las fases del ciclo de vida del proyecto (metodología, fases del proyecto)', 1)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (4, N'SP 1.4', N'Estimar el esfuerzo y el costo', 1)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (5, N'SP 2.1', N'Establecer el presupuesto y el calendario', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (6, N'SP 2.2', N'Identificar los riesgos del proyecto', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (7, N'SP 2.3', N'Planificar la gestión de los datos', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (8, N'SP 2.4', N'Planificar los recursos del proyecto', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (9, N'SP 2.5', N'Planificar el conocimiento y las habilidades necesarias', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (10, N'SP 2.6', N'Planificar la involucración de las partes interesadas', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (11, N'SP 2.7', N'Establecer el plan de proyecto', 2)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (12, N'SP 3.1', N'Revisar los planes que afectan al proyecto', 3)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (13, N'SP 3.2', N'Conciliar los niveles de trabajo y de recursos', 3)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (14, N'SP 3.3', N'Obtener el compromiso con el plan', 3)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (15, N'SP 1.1', N'Establecer los objetivos de medición', 4)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (16, N'SP 1.2', N'Especificar las medidas', 4)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (17, N'SP 1.3', N'Especificar los procedimientos de recogida y almacenamiento de datos', 4)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (18, N'SP 1.4', N'Especificar procedimientos de análisis', 4)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (19, N'SP 2.1', N'Obtener datos de la medición', 5)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (20, N'SP 2.2', N'Analizar los datos de la medición', 5)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (21, N'SP 2.3', N'Almacenar los datos y los resultados', 5)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (22, N'SP 2.4', N'Comunicar los resultados', 5)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (23, N'SP 1.1', N'Comprender los requisitos', 6)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (24, N'SP 1.2', N'Obtener el compromiso', 6)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (25, N'SP 1.3', N'Gestionar los cambios a los requisitos', 6)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (26, N'SP 1.4', N'Mantener la trazabilidad bidireccional de los requisitos', 6)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (27, N'SP 1.5', N'Asegurar el alineamiento entre el trabajo del proyecto y los requisitos', 6)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (28, N'SP 1.1', N'Monitorizar los parámetros de planificación del proyecto', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (29, N'SP 1.2', N'Monitorizar los compromisos', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (30, N'SP 1.3', N'Monitorizar los riesgos del proyecto', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (31, N'SP 1.4', N'Monitorizar la gestión de los datos', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (32, N'SP 1.5', N'Monitorizar la involucración de las partes interesadas', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (33, N'SP 1.6', N'Llevar a cabo las revisiones del progreso', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (34, N'SP 1.7', N'Llevar a cabo las revisiones de hitos', 7)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (35, N'SP 2.1', N'Analizar los problemas', 8)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (36, N'SP 2.2', N'Llevar a cabo las acciones correctivas', 8)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (37, N'SP 2.3', N'Gestionar las acciones correctivas', 8)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (38, N'SP 1.1', N'Identificar los elementos de configuración', 9)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (39, N'SP 1.2', N'Establecer un sistema de gestión de configuración', 9)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (40, N'SP 1.3', N'Crear o liberar las líneas base', 9)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (41, N'SP 2.1', N'Seguir las peticiones de cambio', 10)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (42, N'SP 2.2', N'Controlar los elementos de configuración', 10)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (43, N'SP 3.1', N'Establecer los registros de gestión de configuración', 11)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (44, N'SP 3.2', N'Realizar auditorías de configuración', 11)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (45, N'SP 1.1', N'Evaluar objetivamente los procesos', 12)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (46, N'SP 1.2', N'Evaluar objetivamente los productos de trabajo', 12)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (47, N'SP 2.1', N'Comunicar y resolver las no conformidades', 13)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (48, N'SP 2.2', N'Establecer los registros', 13)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (49, N'SP 1.1', N'Determinar el tipo de adquisición', 14)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (50, N'SP 1.2', N'Seleccionar a los proveedores', 14)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (51, N'SP 1.3', N'Establecer acuerdos con proveedores', 14)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (52, N'SP 2.1', N'Ejecutar el acuerdo con el proveedor', 15)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (53, N'SP 2.2', N'Aceptar el producto adquirido', 15)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (54, N'SP 2.3', N'Asegurar la transición de los productos', 15)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (55, N'SP 1.1', N'Establecer las guías para el análisis de decisiones', 16)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (56, N'SP 1.2', N'Establecer los criterios de evaluación', 16)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (57, N'SP 1.3', N'Identificar las soluciones alternativas', 16)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (58, N'SP 1.4', N'Seleccionar los métodos de evaluación', 16)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (59, N'SP 1.5', N'Evaluar las soluciones altenativas', 16)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (60, N'SP 1.6', N'Seleccionar las soluciones', 16)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (61, N'SP 1.1', N'Establecer el proceso definido del proyecto', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (62, N'SP 1.2', N'Utilizar los activos de proceso de la organización para la planificar las actividades del proyecto', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (63, N'SP 1.3', N'Establecer el entorno del trabajo del proyecto', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (64, N'SP 1.4', N'Integrar los planes', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (65, N'SP 1.5', N'Gestionar el proyecto utilizando planes integrados', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (66, N'SP 1.6', N'Establecer los equipos', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (67, N'SP 1.7', N'Contribuir a los activos de proceso de la organización', 17)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (68, N'SP 2.1', N'Gestionar la involucración de las partes interesadas', 18)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (69, N'SP 2.2', N'Gestionar las dependencias', 18)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (70, N'SP 2.3', N'Resolver las cuestiones de coordinación', 18)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (71, N'SP 1.1', N'Establecer los procesos estandar', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (72, N'SP 1.2', N'Establecer las descripciones de los modelos de ciclo de vida', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (73, N'SP 1.3', N'Establecer los criterios y las guias de adaptación', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (74, N'SP 1.4', N'Establecer el repositorio de mediciones de la organización', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (75, N'SP 1.5', N'Establecer la biblioteca de activos de procesos de la organización', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (76, N'SP 1.6', N'Establecer los estándares del entorno de trabajo', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (77, N'SP 1.7', N'Establecer las reglas y guías para los equipos', 19)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (78, N'SP 1.1', N'Establecer las necesidades de proceso de la organización', 20)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (79, N'SP 1.2', N'Evaluar los procesos de la organización', 20)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (80, N'SP 1.3', N'Identificar las mejoras de los procesos de la organización', 20)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (81, N'SP 2.1', N'Establecer los planes de acción de proceso', 21)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (82, N'SP 2.2', N'Implementar los planes de acción de proceso', 21)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (83, N'SP 3.1', N'Desplegar los activos de proceso de la organización', 22)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (84, N'SP 3.2', N'Desplegar los procesos estándar', 22)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (85, N'SP 3.3', N'Monitorizar la implementación', 22)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (86, N'SP 3.4', N'Incorporar las experiencias en los activos de proceso de la organización', 22)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (87, N'SP 1.1', N'Establecer las necesidades estratégicas de formación', 23)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (88, N'SP 1.2', N'Determinar que necesidades de formación son responsabilidad de la organización', 23)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (89, N'SP 1.3', N'Establecer un plan táctico de formación en la organización', 23)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (90, N'SP 1.4', N'Establecer una capacidad de formación', 23)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (91, N'SP 2.1', N'Impartir la formación', 24)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (92, N'SP 2.2', N'Establecer los registros de formación', 24)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (93, N'SP 2.3', N'Evaluar la eficacia de la formación', 24)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (94, N'SP 1.1', N'Establecer una estratégia de integración', 25)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (95, N'SP 1.2', N'Establecer el entorno de integración del producto', 25)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (96, N'SP 1.3', N'Establecer los procedimientos y los criterios de integración del producto', 25)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (97, N'SP 2.1', N'Revisar la completitud de las descripciones de las interfaces', 26)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (98, N'SP 2.2', N'Gestionar las interfaces', 26)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (99, N'SP 3.1', N'Confirmar la disponibilidad de los componentes de producto para la integración', 27)
GO
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (100, N'SP 3.2', N'Ensamblar los componentes de producto', 27)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (101, N'SP 3.3', N'Evaluar los componentes de producto ensamblados', 27)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (102, N'SP 3.4', N'Empaquetar y entregar el producto o componente de producto', 27)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (103, N'SP 1.1', N'Educir las necesidades', 28)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (104, N'SP 1.2', N'Transformar las necesidades de las partes interesadas en requisitos de cliente', 28)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (105, N'SP 2.1', N'Establecer los requisitos de producto y de componente de producto', 29)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (106, N'SP 2.2', N'Asignar los requisitos de componente de producto', 29)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (107, N'SP 2.3', N'Identificar los requisitos de interfaz', 29)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (108, N'SP 3.1', N'Establecer los conceptos y los escenarios de operación', 30)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (109, N'SP 3.2', N'Establecer una definición de la funcionalidad y de los atributos de calidad requeridos', 30)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (110, N'SP 3.3', N'Analizar los requisitos', 30)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (111, N'SP 3.4', N'Analizar los requisitos para conseguir un equilibrio', 30)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (112, N'SP 3.5', N'Validar los requisitos', 30)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (113, N'SP 1.1', N'Determinar las fuentes y las categorias de los riesgos', 31)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (114, N'SP 1.2', N'Definir los parametros de riesgos', 31)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (115, N'SP 1.3', N'Establecer una estratégia de gestión de riesgos', 31)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (116, N'SP 2.1', N'Identificar los riesgos', 32)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (117, N'SP 2.2', N'Evaluar, clasificar y priorizar los riesgos', 32)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (118, N'SP 3.1', N'Desarrollar los planes de mitigación de riesgos', 33)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (119, N'SP 3.2', N'Implementar los planes de mitigación de riesgos', 33)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (120, N'SP 1.1', N'Desarrollar soluciones alternativas y los criterios de selección', 34)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (121, N'SP 1.2', N'Seleccionar las soluciones de componentes de producto', 34)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (122, N'SP 2.1', N'Diseñar el producto o los componentes de producto', 35)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (123, N'SP 2.2', N'Establecer un paquete de datos técnicos', 35)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (124, N'SP 2.3', N'Diseñar las interfaces usando criterios', 35)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (125, N'SP 2.4', N'Realizar los análisis sobre si hacer, comprar o reutilizar', 35)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (126, N'SP 3.1', N'Implementar el diseño', 36)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (127, N'SP 3.2', N'Desarrollar la documentación de soporte del producto', 36)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (128, N'SP 1.1', N'Seleccionar los productos para la validación', 37)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (129, N'SP 1.2', N'Establecer el entorno para la validación', 37)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (130, N'SP 1.3', N'Establecer los procedimientos y los criterios de validación', 37)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (131, N'SP 2.1', N'Realizar la validación', 38)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (132, N'SP 2.2', N'Analizar los resultados de la validación', 38)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (133, N'SP 1.1', N'Seleccionar los productos de trabajo para la verificación', 39)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (134, N'SP 1.2', N'Establecer el entorno de verificación', 39)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (135, N'SP 1.3', N'Establecer los procedimientos y criterios de verificación', 39)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (136, N'SP 2.1', N'Preparar las revisiones entre pares', 40)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (137, N'SP 2.2', N'Realizar las revisiones entre pares', 40)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (138, N'SP 2.3', N'Analizar los datos de las revisiones entre pares', 40)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (139, N'SP 3.1', N'Realizar la verificación', 41)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (140, N'SP 3.2', N'Analizar los resultados de la verificación', 41)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (141, N'SP 1.1', N'Establecer los objetivos del proyecto', 42)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (142, N'SP 1.2', N'Componer los procesos definidos', 42)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (143, N'SP 1.3', N'Seleccionar los subprocesos y atributos', 42)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (144, N'SP 1.4', N'Seleccionar las técnicas análiticas de medición', 42)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (145, N'SP 2.1', N'Monitorear el rendimiento de los subprocesos seleccionados', 43)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (146, N'SP 2.2', N'Gestionar el rendimiento del proyecto', 43)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (147, N'SP 2.3', N'Realizar análisis de causas de raíz', 43)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (148, N'SP 1.1', N'Establecer objetivos de calidad y procesos', 44)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (149, N'SP 1.2', N'Seleccionar procesos', 44)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (150, N'SP 1.3', N'Establecer medidas de rendimiento de procesos', 44)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (151, N'SP 1.4', N'Analizar rendimiento de procesos y establecer lineas base de rendimiento de procesos', 44)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (152, N'SP 1.5', N'Establecer modelos de rendimiento de procesos', 44)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (153, N'SP 1.1', N'Recolectar y analizar proposiciones de mejora', 45)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (154, N'SP 1.2', N'Identificar y analizar innovaciones', 45)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (155, N'SP 1.3', N'Mejoras piloto', 45)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (156, N'SP 1.4', N'Seleccionar mejoras para despliege', 45)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (157, N'SP 2.1', N'Planificar el despliege de áreas', 46)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (158, N'SP 2.2', N'Gestionar el despliege', 46)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (159, N'SP 2.3', N'Medir los efectos de mejora', 46)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (160, N'SP 1.1', N'Seleccionar resultados por análisis', 47)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (161, N'SP 1.2', N'Analizar causas', 47)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (162, N'SP 2.1', N'Implementar proposiciones de acción', 48)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (163, N'SP 2.2', N'Evaluar el efecto de las acciones implementadas', 48)
INSERT [dbo].[Practica_Especifica] ([id], [nombre], [descripcion], [meta_especifica]) VALUES (164, N'SP 2.3', N'Grabar la información del análisis causal', 48)
SET IDENTITY_INSERT [dbo].[Practica_Especifica] OFF
SET IDENTITY_INSERT [dbo].[Usuario] ON 

INSERT [dbo].[Usuario] ([id], [username], [password], [persona], [rol], [organizacion]) VALUES (2, N'admin@appraisal.com', N'root', 1, 2, NULL)
SET IDENTITY_INSERT [dbo].[Usuario] OFF
SET IDENTITY_INSERT [dbo].[Usuario_Rol] ON 

INSERT [dbo].[Usuario_Rol] ([id], [descripcion]) VALUES (1, N'Organización')
INSERT [dbo].[Usuario_Rol] ([id], [descripcion]) VALUES (2, N'Administrador')
SET IDENTITY_INSERT [dbo].[Usuario_Rol] OFF
/****** Object:  Index [Nivel_lvl_uindex]    Script Date: 7/9/2018 11:43:27 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Nivel_lvl_uindex] ON [dbo].[Nivel]
(
	[lvl] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Area_Proceso]  WITH CHECK ADD  CONSTRAINT [Area_Proceso_Categoria_id_fk] FOREIGN KEY([categoria])
REFERENCES [dbo].[Categoria] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Area_Proceso] CHECK CONSTRAINT [Area_Proceso_Categoria_id_fk]
GO
ALTER TABLE [dbo].[Area_Proceso]  WITH CHECK ADD  CONSTRAINT [Area_Proceso_Nivel_lvl_fk] FOREIGN KEY([nivel])
REFERENCES [dbo].[Nivel] ([lvl])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Area_Proceso] CHECK CONSTRAINT [Area_Proceso_Nivel_lvl_fk]
GO
ALTER TABLE [dbo].[Artefacto]  WITH CHECK ADD  CONSTRAINT [Artefacto_Evidencia_id_fk] FOREIGN KEY([evidencia])
REFERENCES [dbo].[Evidencia] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Artefacto] CHECK CONSTRAINT [Artefacto_Evidencia_id_fk]
GO
ALTER TABLE [dbo].[Evidencia]  WITH CHECK ADD  CONSTRAINT [Evidencia_Instancia_id_fk] FOREIGN KEY([instancia])
REFERENCES [dbo].[Instancia] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Evidencia] CHECK CONSTRAINT [Evidencia_Instancia_id_fk]
GO
ALTER TABLE [dbo].[Evidencia]  WITH CHECK ADD  CONSTRAINT [Evidencia_Practica_Especifica_id_fk] FOREIGN KEY([practica_especifica])
REFERENCES [dbo].[Practica_Especifica] ([id])
GO
ALTER TABLE [dbo].[Evidencia] CHECK CONSTRAINT [Evidencia_Practica_Especifica_id_fk]
GO
ALTER TABLE [dbo].[Hipervinculo]  WITH CHECK ADD  CONSTRAINT [Hipervinculo_Evidencia_id_fk] FOREIGN KEY([evidencia])
REFERENCES [dbo].[Evidencia] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Hipervinculo] CHECK CONSTRAINT [Hipervinculo_Evidencia_id_fk]
GO
ALTER TABLE [dbo].[Instancia]  WITH CHECK ADD  CONSTRAINT [Instancia_Instancia_Tipo_id_fk] FOREIGN KEY([instancia_tipo])
REFERENCES [dbo].[Instancia_Tipo] ([id])
GO
ALTER TABLE [dbo].[Instancia] CHECK CONSTRAINT [Instancia_Instancia_Tipo_id_fk]
GO
ALTER TABLE [dbo].[Instancia]  WITH CHECK ADD  CONSTRAINT [Instancia_Organizacion_id_fk] FOREIGN KEY([organizacion])
REFERENCES [dbo].[Organizacion] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Instancia] CHECK CONSTRAINT [Instancia_Organizacion_id_fk]
GO
ALTER TABLE [dbo].[Meta_Especifica]  WITH CHECK ADD  CONSTRAINT [Meta_Especifica_Area_Proceso_id_fk] FOREIGN KEY([area_proceso])
REFERENCES [dbo].[Area_Proceso] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Meta_Especifica] CHECK CONSTRAINT [Meta_Especifica_Area_Proceso_id_fk]
GO
ALTER TABLE [dbo].[Organizacion]  WITH CHECK ADD  CONSTRAINT [Organizacion_Nivel_lvl_fk] FOREIGN KEY([nivel])
REFERENCES [dbo].[Nivel] ([lvl])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Organizacion] CHECK CONSTRAINT [Organizacion_Nivel_lvl_fk]
GO
ALTER TABLE [dbo].[Practica_Especifica]  WITH CHECK ADD  CONSTRAINT [Practica_Especifica_Meta_Especifica_id_fk] FOREIGN KEY([meta_especifica])
REFERENCES [dbo].[Meta_Especifica] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Practica_Especifica] CHECK CONSTRAINT [Practica_Especifica_Meta_Especifica_id_fk]
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [Usuario_Organizacion_id_fk] FOREIGN KEY([organizacion])
REFERENCES [dbo].[Organizacion] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [Usuario_Organizacion_id_fk]
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [Usuario_Persona_id_fk] FOREIGN KEY([persona])
REFERENCES [dbo].[Persona] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [Usuario_Persona_id_fk]
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [Usuario_Usuario_Rol_id_fk] FOREIGN KEY([rol])
REFERENCES [dbo].[Usuario_Rol] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [Usuario_Usuario_Rol_id_fk]
GO
/****** Object:  StoredProcedure [dbo].[generate]    Script Date: 7/9/2018 11:43:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[generate] AS
  BEGIN
    DECLARE @TranName VARCHAR(20);
    SELECT @TranName = 'MyTransaction';
    DECLARE @area_id INT, @meta_id INT;
    DECLARE area CURSOR FOR SELECT id FROM Area_Proceso;
    OPEN area;
    FETCH NEXT FROM area INTO @area_id;
    WHILE @@FETCH_STATUS = 0 BEGIN
      BEGIN TRANSACTION @TranName;
      BEGIN TRY
      INSERT INTO Meta_Especifica VALUES ('GG 1', 'Lograr metas especificas', @area_id);
        SELECT @meta_id = SCOPE_IDENTITY();
        INSERT INTO Practica_Especifica VALUES ('GP 1.1', 'Realizar practicas especificas', @meta_id);
      INSERT INTO Meta_Especifica VALUES ('GG 2', 'Institucionalizar un proceso gentionado', @area_id);
        SELECT @meta_id = SCOPE_IDENTITY();
        INSERT INTO Practica_Especifica VALUES ('GP 2.1', 'Establecer una politica organizacional', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.2', 'Planificar el proceso', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.3', 'Proveer los recursos', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.4', 'Asignar Responsabilidades', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.5', 'Entrenar personas', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.6', 'Gestionar configuraciones', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.7', 'Identificar e involucrar stakeholders', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.8', 'Monitorear y controlar el proceso', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.9', 'Evaluar adherencia objetivamente', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 2.10', 'Estado de revisión con una gestión de nivel superior', @meta_id);
      INSERT INTO Meta_Especifica VALUES ('GG 3', 'Institucionalizar un proceso definido', @area_id);
        SELECT @meta_id = SCOPE_IDENTITY();
        INSERT INTO Practica_Especifica VALUES ('GP 3.1', 'Establecer un proceso definido', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 3.2', 'Recolectar información de mejora', @meta_id);
      INSERT INTO Meta_Especifica VALUES ('GG 4', 'Institucionalizar un proceso cuantitativo gestionado', @area_id);
        SELECT @meta_id = SCOPE_IDENTITY();
        INSERT INTO Practica_Especifica VALUES ('GP 4.1', 'Establecer objetivos quantitativos para el proceso', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 4.2', 'Estabilizar el rendimiento del subproceso', @meta_id);
      INSERT INTO Meta_Especifica VALUES ('GG 5', 'Institucionalizar un proceso optimizador', @area_id);
        SELECT @meta_id = SCOPE_IDENTITY();
        INSERT INTO Practica_Especifica VALUES ('GP 5.1', 'Asegurar la mejora continua de procesos', @meta_id);
        INSERT INTO Practica_Especifica VALUES ('GP 5.2', 'Corregir las causas de raíz de los problemas', @meta_id);
      COMMIT TRANSACTION @TranName;
      END TRY
      BEGIN CATCH
        ROLLBACK
      END CATCH
    FETCH NEXT FROM area INTO @area_id;
    END
    CLOSE area;
  END
GO
