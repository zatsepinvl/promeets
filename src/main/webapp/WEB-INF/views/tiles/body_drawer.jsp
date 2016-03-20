<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!-- Navigation menu -->
<tiles:insertAttribute name="drawer"/>

<!-- Page body-->
<main class="mdl-layout__content" style="background-color: #f5f5f5">
    <div class="page-content">

        <div class="mdl-grid">

            <!-- Content container -->
            <div class="phone mdl-cell mdl-cell--10-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                <tiles:insertAttribute name="content"/>
            </div>

            <!-- Recommendations -->
            <div class="mdl-cell mdl-cell--2-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                <tiles:insertAttribute name="right_column"/>
            </div>
        </div>
    </div>

</main>