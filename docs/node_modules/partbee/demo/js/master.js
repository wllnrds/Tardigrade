  $('.pb-test-component').change(function(){
    var target = $(this).data('target');
    var value;
    var last;

    if($(this).data('value')){
      value = $(this).data('value');
    }else{
      value = $(this).val();
    }

    if($(this).data('last')){
       last = $(this).data('last');
    }

    if($(this).is("[type=checkbox]")){
      if($(this).is(":checked")){
        $(target).addClass(value);
      }else{
        $(target).removeClass(value);
      }
    }else{
      if(last){
        $(target).removeClass(last);
        $(this).data('last', value);
      }

      $(target).addClass(value);
    }
  });


  $('.float-config').click(function(e){
    if ($(this).is('.active') && e.offsetX < 0) {
      $('.float-config').removeClass('active');
    }else{
      if(!$(this).is('.active')){
        $('.float-config').removeClass('active');
        $(this).addClass('active');
      }
    }
  });

  $('.float-config.active').click(function(e){
  });
