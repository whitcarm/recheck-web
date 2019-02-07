cssAttributes = arguments[0];

function isShown(node) {
	return !(node.offsetParent === null)
}

var result = [];

var f = function(elm) {
	for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode)  {
		for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) { 
			if (sib.localName == elm.localName) {
				i++;
			}   
		}; 
		segs.unshift(elm.localName.toLowerCase() + '[' + i + ']');
	}
	return segs.length ? '/' + segs.join('/') : null;
}

var getStyle = function(node) {
	var result = {}
	// var args = ["align-content", "animation-delay"]
	var style = window.getComputedStyle(node)  || [];

	args.forEach(function(e) {
		result[e] = style[e]
	})

	return result
}

function getText(node) {
	if (node.childNodes[0] && node.childNodes[0].nodeType == 3) {
		return node.childNodes[0].nodeValue;
	}
	return "";
}

document.querySelectorAll('*').forEach(function(node) {
	result.push({
		"path" : f(node),
		"style" : getStyle(node),
		"tagName" : node.tagName,
		"text" : getText(node),
		"shown" : isShown(node)
	})
});

return JSON.stringify(result)
