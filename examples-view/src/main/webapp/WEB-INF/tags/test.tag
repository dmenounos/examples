<%@ taglib uri="http://example.com/pd-functions" prefix="pf" %>

<%@ attribute name="pageContext" required="true" type="javax.servlet.jsp.PageContext" %>
<%@ attribute name="label" required="true" %>

<p>pageContextClass: ${pf:pageContextClass(pageContext)}</p>

<p>label via direct reference: <%=label%></p>
<p>label via jspContext attribute: <%=jspContext.getAttribute("label")%></p>
<p>label via el expression: ${label}</p>
