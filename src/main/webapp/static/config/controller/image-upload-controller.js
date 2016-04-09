define(function (require, exports) {
	var $ = require('jquery'),
		utils = require('utils'),
		$doc = $(document);
	
	exports.init = function (context, images, complete, controller) {
		context = context || $doc;
		images = images || context.find(controller.selector);
		var _count = images.length;
		if (_count > 0) {
			
			require.async(['kindeditor', 'kindeditorcss'], function () {
				images.each(function () {
					_count -= 1;
					var imageId = this.id;
					var $target = $(this);
					if (imageId && $target.data('ke-image-inited')) {
						return true;
					}
					
					$target.data('ke-image-inited', true);
					
					var btnId = utils.random('imageBtn'),
						viewer = $($target.data('image')); // 图片显示容器
					
					viewer.addClass('image-view');
					
					$target.after('<div class="file-upload"><input type="button" value="上传图片" id="' + btnId + '" /></div>');
					
					if (!imageId) {
						imageId = utils.random('image');
						this.id = imageId;
					}
					
					F.uploaders[imageId] = KindEditor.uploadbutton({
						button : document.getElementById(btnId),
						fieldName : 'imgFile',
						url : webContext + '/image/upload',
						afterUpload : function(data) {
							$target.val(data.url);
							viewer.html('<img src="' + data.url + '" />');
						}
					});
					
					F.uploaders[imageId].fileBox.change(function(e) {
						F.uploaders[imageId].submit();
					});
					
					// 初始化图片
					if (this.value && viewer.length) {
						viewer.html('<img src="' + this.value + '" />');
					}
				});
				if (_count === 0) {
					complete(controller.name)
				}
			});
		} else {
			complete(controller.name)
		}
		
	};
});