require xorg-proto-common.inc
PE = "1"
PR = "${INC_PR}.0"

SRC_URI[archive.md5sum] = "7f439166a9b2bf81471a33951883019f"
SRC_URI[archive.sha256sum] = "1baa29931313d0c3eb81dffd42662768cc76ce49ce94024d5fe32ef5a4e8603c"

BBCLASSEXTEND = "native nativesdk sdk"
