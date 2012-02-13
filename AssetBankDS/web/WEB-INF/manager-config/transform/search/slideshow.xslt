<?xml version='1.0'?>
<xsl:stylesheet
    version='1.0'
    xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>

    <xsl:output method="xml" cdata-section-elements="caption" />

	<!-- the template for the root of the autoviewer slideshow xml -->
	<xsl:template match="/">

		<gallery>
		
			<xsl:apply-templates select="search_results/result" />

		</gallery>

	</xsl:template>

	<!-- the template for the autoviewer image elements -->
	<xsl:template match="result">
	
		<!-- make sure we have a display url (i.e. this result is an image) -->
		<xsl:if test="@unwatermarkedlargeUrl">
			<item>
				<xsl:attribute name="assetId"><xsl:value-of select="@id"/></xsl:attribute>
				<image><xsl:value-of select="@unwatermarkedlargeUrl" /></image>
				<thumbnail><xsl:value-of select="@thumbnailUrl" /></thumbnail>
				<title></title>
				<description><xsl:value-of select="caption" disable-output-escaping="yes" /></description>
				<credit><xsl:value-of select="credit" disable-output-escaping="yes" /></credit>
				<width>
					<xsl:choose>
						<xsl:when test="@width">	
							<xsl:value-of select="@width" />
						</xsl:when>
						<xsl:otherwise>
							800
						</xsl:otherwise>
					</xsl:choose>
				</width>
				<height>
					<xsl:choose>
						<xsl:when test="@height">	
							<xsl:value-of select="@height" />
						</xsl:when>
						<xsl:otherwise>
							800
						</xsl:otherwise>
					</xsl:choose>
				</height>
				<link target="_blank"></link>
			</item>
		</xsl:if>
	
	</xsl:template>

</xsl:stylesheet>