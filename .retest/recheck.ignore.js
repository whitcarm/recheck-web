function shouldIgnoreAttributeDifference(element, diff) {
	if (diff.key == 'outline') {
		return (Math.abs(diff.expected.x - diff.actual.x) <= 5) && 
			   (Math.abs(diff.expected.y - diff.actual.y) <= 5) && 
			   (Math.abs(diff.expected.width - diff.actual.width) <= 5) && 
			   (Math.abs(diff.expected.height - diff.actual.height) <= 5);
	}
	if (diff.expected != null && diff.actual != null) {
		re = /http[s]?:\/\/[\w.:\d\-]*/;
		cleanExpected = diff.expected.replace(re, '');
		cleanActual = diff.actual.replace(re, '');
		return cleanExpected === cleanActual;
	}
	return false;
}
