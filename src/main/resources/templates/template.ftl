<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8"/>
	<title>打印模板</title>
	<style type="text/css">
		.template-form-ctn {
		    border: 1px solid #aaa;
		    padding: 20px;
		}
		p.template-form-title {
		    margin: 0;
		    border-bottom: 1px dashed #aaa;
		    padding-bottom: 20px;
		    text-align: center;
		}
		p.template-form-date-item {
		    text-align: right;
		}
		span.template-form-date {
		    margin-right: 100px;
		}

		.template-form{
			border: 1px solid #aaa;
    		overflow: hidden;
		}
		div.template-form-item {
		    float: left;
		    width: 50%;
		    box-sizing: border-box;
		    border-bottom: 1px solid #aaa;
		    border-right: 1px solid #aaa;
		    height: 33px;
		    font-size: 1px;
		}
		/* .template-form-item:nth-last-of-type(1) {
		    border-bottom: 0;
		}
		.template-form-item:nth-last-of-type(2) {
		    border-bottom: 0;
		}
		.template-form-item:nth-of-type(2n+0) {
		    border-right: 0;
		} */
		label.template-form-item-label {
		    width: 120px!important;
		    text-align: left;
		    display: inline-block;
		    height: 100%;
		    line-height: 32px;
		    border-right: 1px solid #aaa;
		    box-sizing: border-box;
		    font-size: 14px;
		    padding-left: 12px;
		}
		span.template-form-item-value {
		    height: 32px;
		    box-sizing: border-box;
		    border-radius: 4px;
		    border: 0;
		    padding: 0 12px;
		    outline: none;
		    width: calc(100% - 120px);
		    font-size: 14px;
			background:#fff!important;
		}
		.template-form-bottom {
		    padding-top: 12px;
		}
		span.template-form-bottom-item {
		    margin-right: 50px;
		    display: inline-block;
		}

	</style>
</head>
<body style="font-family: SimSun;">
	<div class="template-form-ctn">
		<p class="template-form-title">打印凭证</p>
		<p class="template-form-date-item"><span class="template-form-date">日期：2020/01/01</span></p>
		<form class="template-form">
			<div class="template-form-item">
				<label class="template-form-item-label">角色名称：</label>
				<span type="text" class="template-form-item-value" >${user.name}</span>
			</div>
			<div class="template-form-item" style="border-right:0;">
				<label class="template-form-item-label">创建时间：</label>
				<span type="text" class="template-form-item-value" >${createTime}</span>
			</div>	
			<div class="template-form-item">
				<label class="template-form-item-label">创建用户名称：</label>
				<span type="text" class="template-form-item-value" ></span>
			</div>
			<div class="template-form-item" style="border-right:0;">
				<label class="template-form-item-label">更新时间：</label>
				<span type="text" class="template-form-item-value" >${updateTime}</span>
			</div>
			<div class="template-form-item" style="border-bottom:0;">
				<label class="template-form-item-label">更新用户名称：</label>
				<span type="text" class="template-form-item-value" ></span>
			</div>	
			<div class="template-form-item" style="border-bottom:0;border-right:0;">
				<label class="template-form-item-label">角色描述：</label>
				<span type="text" class="template-form-item-value" >${user.desc}</span>
			</div>			
		</form>	
		<div class="template-form-bottom">
			<span class="template-form-bottom-item">记账：</span>
			<span class="template-form-bottom-item">复核：</span>
			<span class="template-form-bottom-item">授权人：</span>
		</div>	
	</div>

</body>
</html>
