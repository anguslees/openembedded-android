DESCRIPTION = "Target packages for a standalone Arago SDK or external toolchain"
ALLOW_EMPTY = "1"
PR = "r18"

PACKAGES = "${PN}"

# Stuff contained in this SDK is largely taken from task-sdk-base.bb.
# This is a starting point, and nothing more at present -- please fill
# this out with a reasonable set of development tools for a Arago image.
# Also feel free to remove stuff that's silly.

RDEPENDS_${PN} = "\
    task-arago-toolchain-target \
#    kernel-source \
    "
