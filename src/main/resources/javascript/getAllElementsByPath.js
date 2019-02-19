cssAttributes = arguments[0];

function isShown(node) {
	return !(node.offsetParent === null)
}

var getStyle = function(node) {
	var result = {}
	var cssAttributes = ["align-content", "animation-delay"]
	var style = window.getComputedStyle(node)  || [];

	cssAttributes.forEach(function(e) {
		result[e] = style[e]
	})

	result.text = getText(node)

	return result
}

function getText(node) {
	if (node.childNodes[0] && node.childNodes[0].nodeType == 3) {
		return node.childNodes[0].nodeValue;
	}
	return "";
}

var getHtml = function(node) {
	return {
		"absolute-outline": {
			"x": "TEST-X",
			"y": "TEST-Y"
		}
	}
}

var getPath = function(node) {
	for (var segs = []; node && node.nodeType == 1; node = node.parentNode)  {
		for (i = 1, sib = node.previousSibling; sib; sib = sib.previousSibling) { 
			if (sib.localName == node.localName) {
				i++;
			}   
		}; 
		segs.unshift(node.localName.toLowerCase() + '[' + i + ']');
	}
	return segs.length ? '/' + segs.join('/') : null;
}

var getSubTree = function(node) {
	if (node.hasChildNodes()) {
		var children = [];
        for (var i = 0; i < node.childNodes.length; i++) {
			var current = getSubTree(node.childNodes[i])
			if (current != null) {
				children.push(current);
			}
        }
		
        return {
			"id": node.nodeName.toLowerCase,
			"path": getPath(node),
			"shown" : isShown(node),
			"attributes": {
				"css": getStyle(node),
				"html": getHtml(node)
			},
			children: children,
        }
    }
}

var nodeTree = getSubTree(document.documentElement)

return JSON.stringify(nodeTree)
