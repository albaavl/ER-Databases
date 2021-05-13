<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<head><title>MedicalTest</title></head>
	<body>
		<p><b>Id:<xsl:value-of select="/medicalTest/@id"/></b></p>
		<p>Patient Id:<xsl:value-of select="/medicalTest/@patient_id"/></p>
		<p>Date:<xsl:value-of select="/medicalTest/@dateMedTest"/></p>
		<p>Type:<xsl:value-of select="/medicalTest/@type"/></p>
		<p>Result:<xsl:value-of select="/medicalTest/@result"/></p>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>
