// Trims white space for code elements
$('.prettyprint').each(function () {
  $(this).html($(this).html().trim());
});
$('.prettyprint .code').each(function () {
  $(this).html($(this).html().trim());
});

const operators = ['or', 'and', 'after', 'before', 'eq', 'ageAt', 'greaterOrEquals', 'greaterThan', 'lesserOrEquals', 'lesserThan', 'startsWith', 'matches']

const doov = function (args) {
  // Add operator CSS classes
  $('.prettyprinted .pln').each(function () {
    if ($(this).text().trim() == 'DOOV') {
      $(this).addClass('doov');
    } else if (operators.includes($(this).text().trim())) {
      $(this).addClass('operator');
    }
  });
}

// Callback after the code is pretty printed
window.exports = { 'doov': doov };

const setFooterVisibility = function() {
  const footer = document.getElementById('footer');
  if (footer) {
    if (Reveal.isFirstSlide()) {
      footer.className = footer.className.replace('show', 'hide');
    } else {
      footer.className = footer.className.replace('hide', 'show');
    }
  }
}
setFooterVisibility();
Reveal.addEventListener('slidechanged', function(event) { setFooterVisibility(); });

