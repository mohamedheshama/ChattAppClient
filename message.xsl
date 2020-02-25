<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h1>TestMessagesHTML</h1>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="message">
        <xsl:if test="from">
            <li style="
                        list-style-type: none;
                        margin-bottom: 3px;
                        padding: 5px 10px;
                        clear: both;
                        border-radius: 10px 10px 2px 2px ;
                        background: #0066ff;
                        color: white;
                        float: right;
                        border-bottom-left-radius: 10px;">
                <xsl:apply-templates select="content"/>
            </li><br/>
        </xsl:if>
        <xsl:if test="to">
            <li style="
                        list-style-type: none;
                        margin-bottom: 3px;
                        padding: 5px 10px;
                        clear: both;
                        border-radius: 10px 10px 2px 2px ;
                        background: #f2f2f2;
                        color: black;
                        float: left;
                        border-bottom-right-radius: 10px;">
                <xsl:apply-templates select="content"/>
            </li><br/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="content">
        <span>
            <xsl:attribute name="style">
                <xsl:text>color:</xsl:text>
                <xsl:value-of select="../color" />
                <xsl:text>;</xsl:text>

                <xsl:text>font-size:</xsl:text>
                <xsl:value-of select="../fontSize" />
                <xsl:text>px;</xsl:text>

                <xsl:text>font-weight:</xsl:text>
                <xsl:value-of select="../fontWeight" />
                <xsl:text>;</xsl:text>

            </xsl:attribute>
            <xsl:value-of select="."/>
        </span>

    </xsl:template>

</xsl:stylesheet>