require patch.inc

SRC_URI = "${GNU_MIRROR}/patch/patch-2.5.4.tar.gz \
	   file://2.5.9.patch;patch=1 \
	   file://debian.patch;patch=1 \
	   file://install.patch;patch=1 \
           file://unified-reject-files.diff;patch=1 \
           file://global-reject-file.diff;patch=1 "
S = "${WORKDIR}/patch-2.5.4"
PR = "r2"
SRC_URI[md5sum] = "ee5ae84d115f051d87fcaaef3b4ae782"
SRC_URI[sha256sum] = "dd2fc5a745bfca5450d13d7032fdc47ab102514aae3efb3fe334a6eff87df799"
