require xorg-proto-common.inc
PE = "1"
PR = "${INC_PR}.0"

SRC_URI[archive.md5sum] = "1a2b31430d04340be2e49e8b6445e076"
SRC_URI[archive.sha256sum] = "fd8cd0bc3e94d7416e6af9eddfa909b0e64246e0d2bb41c00f4e02bd85c76bee"

BBCLASSEXTEND = "native nativesdk sdk"
