<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<head><title>Shift</title></head>
	<body>
		<p><b>Shift Id:<xsl:value-of select="/shift/@id"/></b></p>
		<p>Date:<xsl:value-of select="/shift/@date"/></p>
		<p>Turn:<xsl:value-of select="/shift/@turn"/></p>
		<p>Room:<xsl:value-of select="/shift/@room"/></p>
		<table border="1">
      <th>Worker</th>
      <th>Name</th>
      <th>Surname</th>
      <th>Specialty</th>
      <th>Role</th>
      <xsl:for-each select="Shift/Workers/Worker">
      <xsl:sort select="@id" />
	        <td><i><xsl:value-of select="@name" /></i></td>
	        <td><i><xsl:value-of select="@surname" /></i></td>
	        <td><xsl:value-of select="specialty" /></td>
	        <td><xsl:value-of select="roles" /></td>
      </xsl:for-each>
    </table>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>