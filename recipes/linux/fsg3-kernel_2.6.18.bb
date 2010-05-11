# Kernel for Freecom FSG-3
#
# This bitbake file pulls patches and the defconfig straight
# from the NSLU2-Linux SVN repository. Information about this
# repository can be found at:
# http://trac.nslu2-linux.org/kernel/
#
# The revision that is pulled from SVN is specified below
IXP4XX_KERNEL_SVN_REV = "902"
#
# The directory containing the patches to be applied is
# specified below
IXP4XX_KERNEL_PATCH_DIR = "2.6.18-fsg3"
#
# Increment the number below (i.e. the digits after PR) when
# the changes in SVN between revisions include changes in the
# patches applied to the kernel, rather than simply defconfig
# changes
PR = "r1.${IXP4XX_KERNEL_SVN_REV}"

require ixp4xx-kernel.inc
require ixp4xx-kernel-svnpatch.inc

SRC_URI[kernel.md5sum] = "296a6d150d260144639c3664d127d174"
SRC_URI[kernel.sha256sum] = "c95280ff6c5d2a17788f7cc582d23ae8a9a7ba3f202ec6e4238eaadfce7c163d"
