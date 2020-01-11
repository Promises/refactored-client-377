refactored-client-377
=================

Refactoring the 377 deob. And extending it without adding rice.

-----

### Client Config

Copy `EXAMPLE-client-config.yaml` file and paste it into the same `/config` directory. Rename the new file to `client-config.yaml` and make your config edits to that file. This file will not be tracked within the repository.


-----

### New Features

* Mouse wheel zoom, scroll, and move view
* Sound engine thanks to galkon (will be replaced with proper 400+)
* 508+ and legacy text rendering engine: supports
    * all the old @red@ @str@ etc
    * all the new : (credits to a 508 deob and stewie for his half assed work)
        *     <col=######></col> Custom colors
              <shad=######></shad> - Custom shadow color
              <shad></shad> - Default shadow color
              <trans=TRANS_AMOUNT></trans>
              <u=######></u> Custom underlining color
              <u></u> Default underlining
              <str=######></str> Custom Strikethrough color
              <str></str> Default strikethorough
              <img=#> whatever image you'd like
