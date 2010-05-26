# Bitbake recipe for the madwifi-ng driver

# Disable stripping of kernel modules, since this action strips too
# much out, and the resulting module won't load.
PACKAGE_STRIP = "no"

require madwifi-ng_r.inc

# Due to a minor Makefile restructuring, newer versions require an updated
# patch; this is really ugly and some alternate way to do this that's more
# generic should be figured out.
WACKELF_SRC_URI_ixp4xx =          " file://20-xscale-VFP-wackelf-v2.patch"
WACKELF_SRC_URI_compulab-pxa270 = " file://20-xscale-VFP-wackelf-v2.patch"

SRC_URI += "file://respect-ldflags.patch"
SRC_URI += "file://madwifi-fix-2.6.30.patch"

SRCNAME = "madwifi-trunk"

# PR set after the include, to override what's set in the included file.
PR = "r2"

SRC_URI[md5sum] = "d6e04fa7fb0bea5079f24a753633fb29"
SRC_URI[sha256sum] = "d504e1e7b8f0d01b5441a86d1086aa4088c8896d87d8aabd6dabace76997467a"
