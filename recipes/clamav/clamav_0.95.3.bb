require clamav.inc

SRC_URI += "file://clamav-fix-_SC_PAGESIZE-missing.patch"

SRC_URI[clamav-0.95.3.md5sum] = "eaf9fccc3cc3567605a9732313652967"
SRC_URI[clamav-0.95.3.sha256sum] = "003e7a570932fdffbd19fa7a7996274fbfc93f890d26c3066a36eb824c906250"

PR = "${INC_PR}.1"
