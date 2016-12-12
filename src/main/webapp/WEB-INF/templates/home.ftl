<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home | Ian He</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="iHelin"/>
    <meta name="description" content="iHelin的博客。"/>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" href="${request.contextPath}/css/home.css">
</head>
<body>
<div id="scene" class="stars-wrapper">
    <div data-depth="0.00" class="layer"></div>
    <div data-depth="0.30" class="stars-cluster stars-cluster-1 layer">
        <div class="star star-1"></div>
        <div class="star star-2"></div>
        <div class="star star-3"></div>
        <div class="star star-4"></div>
        <div class="star star-5"></div>
        <div class="star star-6"></div>
        <div class="star star-7"></div>
        <div class="star star-8"></div>
        <div class="star star-9"></div>
        <div class="star star-10"></div>
        <div class="star star-11"></div>
        <div class="star star-12"></div>
        <div class="star star-13"></div>
        <div class="star star-14"></div>
        <div class="star star-15"></div>
        <div class="star star-16"></div>
        <div class="star star-17"></div>
        <div class="star star-18"></div>
        <div class="star star-19"></div>
        <div class="star star-20"></div>
        <div class="star star-21"></div>
        <div class="star star-22"></div>
        <div class="star star-23"></div>
        <div class="star star-24"></div>
        <div class="star star-25"></div>
        <div class="star star-26"></div>
        <div class="star star-27"></div>
        <div class="star star-28"></div>
        <div class="star star-29"></div>
        <div class="star star-30"></div>
        <div class="star star-31"></div>
        <div class="star star-32"></div>
        <div class="star star-33"></div>
        <div class="star star-34"></div>
        <div class="star star-35"></div>
        <div class="star star-36"></div>
        <div class="star star-37"></div>
        <div class="star star-38"></div>
        <div class="star star-39"></div>
        <div class="star star-40"></div>
    </div>
    <div data-depth="0.50" class="stars-cluster stars-cluster-2 layer">
        <div class="star star-1"></div>
        <div class="star star-2"></div>
        <div class="star star-3"></div>
        <div class="star star-4"></div>
        <div class="star star-5"></div>
        <div class="star star-6"></div>
        <div class="star star-7"></div>
        <div class="star star-8"></div>
        <div class="star star-9"></div>
        <div class="star star-10"></div>
        <div class="star star-11"></div>
        <div class="star star-12"></div>
        <div class="star star-13"></div>
        <div class="star star-14"></div>
        <div class="star star-15"></div>
        <div class="star star-16"></div>
        <div class="star star-17"></div>
        <div class="star star-18"></div>
        <div class="star star-19"></div>
        <div class="star star-20"></div>
        <div class="star star-21"></div>
        <div class="star star-22"></div>
        <div class="star star-23"></div>
        <div class="star star-24"></div>
        <div class="star star-25"></div>
        <div class="star star-26"></div>
        <div class="star star-27"></div>
        <div class="star star-28"></div>
        <div class="star star-29"></div>
        <div class="star star-30"></div>
        <div class="star star-31"></div>
        <div class="star star-32"></div>
        <div class="star star-33"></div>
        <div class="star star-34"></div>
        <div class="star star-35"></div>
        <div class="star star-36"></div>
        <div class="star star-37"></div>
        <div class="star star-38"></div>
        <div class="star star-39"></div>
        <div class="star star-40"></div>
    </div>
    <div data-depth="0.90" class="stars-cluster stars-cluster-3 layer">
        <div class="star star-1"></div>
        <div class="star star-2"></div>
        <div class="star star-3"></div>
        <div class="star star-4"></div>
        <div class="star star-5"></div>
        <div class="star star-6"></div>
        <div class="star star-7"></div>
        <div class="star star-8"></div>
        <div class="star star-9"></div>
        <div class="star star-10"></div>
        <div class="star star-11"></div>
        <div class="star star-12"></div>
        <div class="star star-13"></div>
        <div class="star star-14"></div>
        <div class="star star-15"></div>
    </div>
    <div data-depth="0.30" class="moon-wrapper layer">
        <div class="moon"></div>
    </div>
    <div data-depth="0.40" class="title-wrapper layer">
        <a href="${request.contextPath}/index"><h1 data-title="iHelin's World" class="title">iHelin's World</h1></a>
    </div>
</div>
<script src='${request.contextPath}/js/jquery-2.2.1.min.js'></script>
<script src='${request.contextPath}/js/jquery.parallax.js'></script>
</body>
<script>
    $(function () {
        $('#scene').parallax({
            calibrateX: true,
            calibrateY: true,
            scalarX: 12,
            scalarY: 12,
            frictionX: 0.1,
            frictionY: 0.1,
            originX: 0.5,
            originY: 0.5
        });

        setTimeout(function () {
            window.location.href = '${request.contextPath}/index';
        }, 13000);
    });

</script>
</html>
