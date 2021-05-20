<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<head><title>Worker</title></head>
	<body>
		<p><b>Worker Id:<xsl:value-of select="/worker/@workerId"/></b></p>
		<p>Name:<xsl:value-of select="/worker/@workerName"/></p>
		<p>Surname:<xsl:value-of select="/worker/@workerSurname"/></p>
		<p>UserId:<xsl:value-of select="/worker/@userId"/></p>
		<p>Specialty:<xsl:value-of select="/worker/@specialtyId"/></p>
		<p>Role:<xsl:value-of select="/worker/@typeWorker"/></p>
		<table border="1">
	  <th>Shift</th>
      <th>Shift Id</th>
      <th>Date</th>
      <th>Turn</th>
      <th>Room</th>
      <xsl:for-each select="Worker/Shifts/Shift">
      <xsl:sort select="@shiftId" />
	        <td><xsl:value-of select="date" /></td>
	        <td><xsl:value-of select="turn" /></td>
	        <td><xsl:value-of select="room" /></td>
      </xsl:for-each>
    </table>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>