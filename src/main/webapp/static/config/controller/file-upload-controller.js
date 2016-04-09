define(function (require, exports) {
	var $ = require('jquery'),
		utils = require('utils'),
		$doc = $(document);
	
	function generateFile(file, fileId) {
		var link = [];
		var url = webContext + '/file/download/' + file.id;
		var delUrl = webContext + '/file/delete?id=' + file.id;
		link.push('<div class="file-item" data-target="#' + fileId + '">');
		link.push('  <a href="' + url + '" target="_blank" title="' + file.realFileName + '" class="file-name">');
		link.push('<i class="icon-download"></i> ');
		link.push(file.realFileName);
		link.push('</a>');
		link.push('  <a href="' + delUrl + '" class="file-del" title="删除"><i class="icon-remove"></i></a>');
		link.push('</div>');
		return link.join('');
	}
	
	function generateViewFile(file) {
		var link = [];
		var url = webContext + '/file/download/' + file.id;
		link.push('<div class="file-item">');
		link.push('  <a href="' + url + '" target="_blank" title="' + file.realFileName + '" class="file-name">');
		link.push('<i class="icon-download"></i> ');
		link.push(file.realFileName);
		link.push('</a>');
		link.push('</div>');
		return link.join('');
	}
	
	$doc.on('click.delete-files', '.file-del', function () {
		var $this = $(this);
		$.post(this.href).done(function () {
			var fileItem = $this.closest('.file-item');
			var target = $(fileItem.data('target'));
			if (fileItem.siblings('.file-item').length === 0) {
				target.data('old-entity-key', target.val()); // 清空附件后，记录原entityKey
				target.val('');
			}
			$(F.uploaders[target[0].id].form[0]).find('[name=entityKey]').val('');
			fileItem.remove();
		});
		return false;
		
	});
	
	exports.init = function (context, attachments, complete, controller) {
		context = context || $doc;
		attachments = attachments || context.find(controller.selector);
		var _count = attachments.length;
		if (_count > 0) {
			require.async(['kindeditor', 'kindeditorcss'], function () {
				attachments.each(function() {
					_count -= 1;
					var fileId = this.id;
					var $target = $(this);
					// 此处判断有问题，如果页面某部分重新加载了，id不变的话，上传将不会被初始化
					// 改判断方法为对象data属性
					if (fileId && $target.data('ke-upload-inited')) {
						return true;
					}
					
					$target.data('ke-upload-inited', true);
					
					var btnId = utils.random('fileBtn'),
						options = utils.json2obj($target.data('attachment')),
						length = options.length,
						maxSize = options.maxSize || 10,
						viewer = $(options.target);
					
					viewer.addClass('upload-ctrl');
					
					$target.after('<div class="file-upload"><input type="button" value="上传附件" id="' + btnId + '" /><span class="file-upload-descn">请上传<10M的附件</span></div>');
					
					if (!fileId) {
						fileId = utils.random('file');
						this.id = fileId;
					}
					
					F.uploaders[fileId] = KindEditor.uploadbutton({
						button : document.getElementById(btnId),
						fieldName : 'file',
						extraParams : {
							entityKey : $target.val(),
							maxSize: maxSize
						},
						url : webContext + '/file/upload',
						afterUpload : function(data) {
							if (!data.error) {
								var link = generateFile(data, fileId);
								viewer.append(link);
								
								if (!$target.val()) {
									$target.val(data.entityKey);
									$target.data('old-entity-key', data.entityKey);
									$(F.uploaders[fileId].form[0]).find('[name=entityKey]').val(data.entityKey);
								}
							} else {
								$.alert(data.errorMsg);
							}
						}
					});
					
					F.uploaders[fileId].fileBox.change(function(e) {
						var old_entityKey = $target.data('old-entity-key');
						var entityKey = $target.val();
						if (!entityKey && old_entityKey) { 
							// 如果当前input:hidden的value为空，且之前有值
							// 说明这是清空了附件之后的再次上传附件操作，此时还原旧entityKey，以保证原key中的附件还关联在业务中
							
							entityKey = old_entityKey;
							$target.val(old_entityKey);
							$(F.uploaders[fileId].form[0]).find('[name=entityKey]').val(old_entityKey);
						}
						if (length > 0 && entityKey) {
							var fileLength = viewer.find('.file-item').length;
							if (length === 1 && fileLength === 1) {
								$.alert('只能上传一份附件，如需修改，请先删除之前上传的附件。');
							} else if (fileLength >= length) {
								$.alert('最多只能上传 <b>' + length + '</b> 份附件。');
							} else {
								F.uploaders[fileId].submit();
							}
						} else {
							F.uploaders[fileId].submit();
						}
					});
					
					if (this.value && viewer.length) {
						$.getJSON(webContext + '/file/getFileList/' + this.value)
						.done(function (data) {
							if (data.data.length > 0) {
								var list = $.map(data.data, function (file) {
									return generateFile(file, fileId);
								}).join('');
								
								viewer.html(list);
							} else {
								this.value = ''; // key中包含的全是无效被删除的附件，清空值
							}
						});
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
	
	exports.load = function (context, attachmentsViewer, complete, controller) {
		context = context || $doc;
		attachmentsViewer = attachmentsViewer || context.find(controller.selector);
		var _count = attachmentsViewer.length;
		if (_count > 0) {
			attachmentsViewer.each(function() {
				_count -= 1;
				var $this = $(this), 
					entityKey = $this.data('attachment-list');
				if (entityKey) {
					$.getJSON(webContext + '/file/getFileList/' + entityKey)
					.done(function (data) {
						if (data.data.length) {
							var list = $.map(data.data, function (file) {
								return generateViewFile(file);
							}).join('');
							
							$this.addClass('file-view').html(list);
						}
					});
				}
			});
			
			if (_count === 0) {
				complete(controller.name)
			}
		} else {
			complete(controller.name)
		}
	};
	
	
});