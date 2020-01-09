<!DOCTYPE html>
<html lang="zh_CN">
<head>
<#include "../psmpCommon/_common.ftl"/>
    <title>${title}</title>
    <style type="text/css">
        .dong {
            width: 1252px;
            height: 512px;
            position: relative;
            background-image: url("/eac/static/hui/images/house/map.jpg");
        }

        .dong-line {
            margin: 16px 30px;
            position: absolute;
            /*display: flex;*/
            /*justify-content: space-between;*/
        }

        .dong-line > div {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .dong-item {
            display: inline-block;
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background-image: url("/eac/static/hui/images/house/dong8.jpg");
            background-size: 220%;
            background-position: -28px -36px;
        }

        .dong-item:hover {
            border: 1px #ff811a solid;
            box-shadow: 0 0 15px 2px #ff811a inset;
            cursor: pointer;
        }

        #infoWindow {
            position: absolute;
            background-color: white;
            padding: 10px;
            border: 1px #ff811a solid;
            border-radius: 4px;
        }
    </style>
</head>

<body>
<div class="list-whole">
    <div class="dong">
        <div class="dong-line" style="top: 66px;left: 164px;">
            <div><span class="dong-item"></span><label>XX家园 1号楼</label></div>
        </div>
        <div class="dong-line" style="top: 66px;left: 430px;">
            <div><span class="dong-item"></span><label>XX家园 2号楼</label></div>
        </div>
        <div class="dong-line" style="top: 66px;left: 700px;">
            <div><span class="dong-item"></span><label>XX家园 3号楼</label></div>
        </div>
        <div class="dong-line" style="top: 223px;left: 164px;">
            <div><span class="dong-item"></span><label>XX家园 4号楼</label></div>
        </div>
        <div class="dong-line" style="top: 223px;left: 430px;">
            <div><span class="dong-item"></span><label>XX家园 5号楼</label></div>
        </div>
        <div class="dong-line" style="top: 223px;left: 700px;">
            <div><span class="dong-item"></span><label>XX家园 6号楼</label></div>
        </div>
        <div class="dong-line" style="top: 378px;left: 164px;">
            <div><span class="dong-item"></span><label>XX家园 7号楼</label></div>
        </div>
        <div class="dong-line" style="top: 378px;left: 430px;">
            <div><span class="dong-item"></span><label>XX家园 8号楼</label></div>
        </div>
        <div class="dong-line" style="top: 378px;left: 700px;">
            <div><span class="dong-item"></span><label>XX家园 9号楼</label></div>
        </div>
    </div>
    <div id="infoWindow" style="display: none;">
        <div style="text-align: center;font-weight: bold;margin-bottom: 4px;"><label>建筑信息</label></div>
        <div><label>楼房名称:</label><label>XXXX楼</label></div>
        <div><label>完工时间:</label><label>1992-05-26</label></div>
        <div><label>楼高:</label><label>35米</label></div>
        <div><label>物业公司:</label><label>XXXX物业</label></div>
    </div>

</div>
<script type="text/javascript">
    var mark = false;
    $(function () {
        $(document).on("mousemove", ".dong-item", function () {
            if (mark) {
                var odiv = $("#infoWindow")[0];
                var width = $("#infoWindow").width();
                var height = $("#infoWindow").height();
                var top = parseInt(window.event.y - height - 30);
                if (top >= 0) odiv.style.top = top + "px";
                else odiv.style.top = parseInt(window.event.y + 30) + "px";
                var left = parseInt(window.event.x - width - 30);
                if (left >= 0) odiv.style.left = left + "px";
                else  odiv.style.left = parseInt(window.event.x + 30) + "px";
            }
        });
        $(document).on("mouseover", ".dong-item", function () {
            $("#infoWindow").show();
            mark = true;
        });
        $(document).on("mouseout", ".dong-item", function () {
            $("#infoWindow").hide();
            mark = false;
        })
    });
</script>
</body>
</html>

