<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<head><title>Shift</title></head>
	<body>
		<p><b>Shift Id:<xsl:value-of select="/shift/@shiftId"/></b></p>
		<p>Date:<xsl:value-of select="/shift/@date"/></p>
		<p>Turn:<xsl:value-of select="/shift/@turn"/></p>
		<p>Room:<xsl:value-of select="/shift/@room"/></p>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>