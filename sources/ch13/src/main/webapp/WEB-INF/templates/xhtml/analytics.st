<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-374617-2']);
  _gaq.push(['_trackPageview']);
  $if(loadtime)$
  _gaq.push(['_trackEvent', 'Load', 'Page Load Time',
    'Page Load Time', $loadtime$]);
  $endif$
  $if(gaestatus)$
  _gaq.push(['_trackEvent', 'Status', 'GAE Status', '$gaestatus$']);
  $endif$

  (function() {
    var ga = document.createElement('script');
    ga.type = 'text/javascript';
    ga.async = true;
    ga.src = ('https:' == document.location.protocol ?
                          'https://ssl' : 'http://www')
             + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(ga, s);
  })();
</script>