const operators = ['or', 'and', 'after', 'before', 'eq', 'ageAt', 'greaterOrEquals', 'greaterThan', 'lesserOrEquals', 'lesserThan', 'startsWith', 'matches']

const doov = function (args) {
  $('.prettyprinted .pln').each(function () {
    if ($(this).text().trim() == 'DOOV') {
      $(this).addClass('doov');
    } else if (operators.includes($(this).text().trim())) {
      $(this).addClass('operator');
    }
  });
}

window.exports = { 'doov': doov };
