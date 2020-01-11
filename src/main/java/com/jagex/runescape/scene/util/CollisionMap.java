package com.jagex.runescape.scene.util;

public class CollisionMap {

	public int insetX;
	public int insetY;
	public int width;
	public int height;
	public int[][] adjacency;

	public CollisionMap(int height, int width) {
		insetX = 0;
		insetY = 0;
		this.width = width;
		this.height = height;
		adjacency = new int[width][height];
		reset();

	}

	public void reset() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++)
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
					adjacency[x][y] = 0xffffff;
				else
					adjacency[x][y] = 0x1000000;

		}

	}

	public void markWall(int x, int y, int position, int orientation, boolean impenetrable) {
		x -= insetX;
		y -= insetY;
		if (position == 0) {
			if (orientation == 0) {
				set(x, y, 128);
				set(x - 1, y, 8);
			}
			if (orientation == 1) {
				set(x, y, 2);
				set(x, y + 1, 32);
			}
			if (orientation == 2) {
				set(x, y, 8);
				set(x + 1, y, 128);
			}
			if (orientation == 3) {
				set(x, y, 32);
				set(x, y - 1, 2);
			}
		}
		if (position == 1 || position == 3) {
			if (orientation == 0) {
				set(x, y, 1);
				set(x - 1, y + 1, 16);
			}
			if (orientation == 1) {
				set(x, y, 4);
				set(x + 1, y + 1, 64);
			}
			if (orientation == 2) {
				set(x, y, 16);
				set(x + 1, y - 1, 1);
			}
			if (orientation == 3) {
				set(x, y, 64);
				set(x - 1, y - 1, 4);
			}
		}
		if (position == 2) {
			if (orientation == 0) {
				set(x, y, 130);
				set(x - 1, y, 8);
				set(x, y + 1, 32);
			}
			if (orientation == 1) {
				set(x, y, 10);
				set(x, y + 1, 32);
				set(x + 1, y, 128);
			}
			if (orientation == 2) {
				set(x, y, 40);
				set(x + 1, y, 128);
				set(x, y - 1, 2);
			}
			if (orientation == 3) {
				set(x, y, 160);
				set(x, y - 1, 2);
				set(x - 1, y, 8);
			}
		}
		if (impenetrable) {
			if (position == 0) {
				if (orientation == 0) {
					set(x, y, 0x10000);
					set(x - 1, y, 4096);
				}
				if (orientation == 1) {
					set(x, y, 1024);
					set(x, y + 1, 16384);
				}
				if (orientation == 2) {
					set(x, y, 4096);
					set(x + 1, y, 0x10000);
				}
				if (orientation == 3) {
					set(x, y, 16384);
					set(x, y - 1, 1024);
				}
			}
			if (position == 1 || position == 3) {
				if (orientation == 0) {
					set(x, y, 512);
					set(x - 1, y + 1, 8192);
				}
				if (orientation == 1) {
					set(x, y, 2048);
					set(x + 1, y + 1, 32768);
				}
				if (orientation == 2) {
					set(x, y, 8192);
					set(x + 1, y - 1, 512);
				}
				if (orientation == 3) {
					set(x, y, 32768);
					set(x - 1, y - 1, 2048);
				}
			}
			if (position == 2) {
				if (orientation == 0) {
					set(x, y, 0x10400);
					set(x - 1, y, 4096);
					set(x, y + 1, 16384);
				}
				if (orientation == 1) {
					set(x, y, 5120);
					set(x, y + 1, 16384);
					set(x + 1, y, 0x10000);
				}
				if (orientation == 2) {
					set(x, y, 20480);
					set(x + 1, y, 0x10000);
					set(x, y - 1, 1024);
				}
				if (orientation == 3) {
					set(x, y, 0x14000);
					set(x, y - 1, 1024);
					set(x - 1, y, 4096);
				}
			}
		}
	}

	public void method413(int y, int orient, int h, int w, boolean impenetrable, int x, byte byte0) {
		int occupied = 256;
		if (impenetrable)
			occupied += 0x20000;
		x -= insetX;
		y -= insetY;
		if (orient == 1 || orient == 3) {
			int tmp = w;
			w = h;
			h = tmp;
		}
		for (int l1 = x; l1 < x + w; l1++)
			if (l1 >= 0 && l1 < width) {
				for (int i2 = y; i2 < y + h; i2++)
					if (i2 >= 0 && i2 < height)
						set(l1, i2, occupied);

			}

	}

	public void markBlocked(int x, int y) {
		x -= insetX;
		y -= insetY;
		adjacency[x][y] |= 0x200000;
	}

	public void set(int x, int y, int flag) {
		adjacency[x][y] |= flag;
	}

	public void unmarkWall(int orientation, int x, int y, int position, boolean impenetrable) {
		x -= insetX;
		y -= insetY;
		if (position == 0) {
			if (orientation == 0) {
				unset(x, y, 128);
				unset(x - 1, y, 8);
			}
			if (orientation == 1) {
				unset(x, y, 2);
				unset(x, y + 1, 32);
			}
			if (orientation == 2) {
				unset(x, y, 8);
				unset(x + 1, y, 128);
			}
			if (orientation == 3) {
				unset(x, y, 32);
				unset(x, y - 1, 2);
			}
		}
		if (position == 1 || position == 3) {
			if (orientation == 0) {
				unset(x, y, 1);
				unset(x - 1, y + 1, 16);
			}
			if (orientation == 1) {
				unset(x, y, 4);
				unset(x + 1, y + 1, 64);
			}
			if (orientation == 2) {
				unset(x, y, 16);
				unset(x + 1, y - 1, 1);
			}
			if (orientation == 3) {
				unset(x, y, 64);
				unset(x - 1, y - 1, 4);
			}
		}
		if (position == 2) {
			if (orientation == 0) {
				unset(x, y, 130);
				unset(x - 1, y, 8);
				unset(x, y + 1, 32);
			}
			if (orientation == 1) {
				unset(x, y, 10);
				unset(x, y + 1, 32);
				unset(x + 1, y, 128);
			}
			if (orientation == 2) {
				unset(x, y, 40);
				unset(x + 1, y, 128);
				unset(x, y - 1, 2);
			}
			if (orientation == 3) {
				unset(x, y, 160);
				unset(x, y - 1, 2);
				unset(x - 1, y, 8);
			}
		}
		if (impenetrable) {
			if (position == 0) {
				if (orientation == 0) {
					unset(x, y, 0x10000);
					unset(x - 1, y, 4096);
				}
				if (orientation == 1) {
					unset(x, y, 1024);
					unset(x, y + 1, 16384);
				}
				if (orientation == 2) {
					unset(x, y, 4096);
					unset(x + 1, y, 0x10000);
				}
				if (orientation == 3) {
					unset(x, y, 16384);
					unset(x, y - 1, 1024);
				}
			}
			if (position == 1 || position == 3) {
				if (orientation == 0) {
					unset(x, y, 512);
					unset(x - 1, y + 1, 8192);
				}
				if (orientation == 1) {
					unset(x, y, 2048);
					unset(x + 1, y + 1, 32768);
				}
				if (orientation == 2) {
					unset(x, y, 8192);
					unset(x + 1, y - 1, 512);
				}
				if (orientation == 3) {
					unset(x, y, 32768);
					unset(x - 1, y - 1, 2048);
				}
			}
			if (position == 2) {
				if (orientation == 0) {
					unset(x, y, 0x10400);
					unset(x - 1, y, 4096);
					unset(x, y + 1, 16384);
				}
				if (orientation == 1) {
					unset(x, y, 5120);
					unset(x, y + 1, 16384);
					unset(x + 1, y, 0x10000);
				}
				if (orientation == 2) {
					unset(x, y, 20480);
					unset(x + 1, y, 0x10000);
					unset(x, y - 1, 1024);
				}
				if (orientation == 3) {
					unset(x, y, 0x14000);
					unset(x, y - 1, 1024);
					unset(x - 1, y, 4096);
				}
			}
		}
	}

	public void unmarkSolidOccupant(int impenetrable, int y, int x, int orientation, int height, boolean flag, int width) {
		int occupied = 256;
		x -= insetX;
		y -= insetY;
		if (orientation == 1 || orientation == 3) {
			int originalWidth = width;
			width = height;
			height = originalWidth;
		}
		for (int xCounter = x; xCounter < x + width; xCounter++)
			if (xCounter >= 0 && xCounter < this.width) {
				for (int yCounter = y; yCounter < y + height; yCounter++)
					if (yCounter >= 0 && yCounter < this.height)
						unset(xCounter, yCounter, occupied);

			}

	}

	public void unset(int x, int y, int flag) {
		adjacency[x][y] &= 0xffffff - flag;
	}

	public void unmarkConcealed(int x, int y) {
			x -= insetX;
			y -= insetY;
			adjacency[x][y] &= 0xdfffff;
	}

	public boolean reachedWall(int currentX, int currentY, int goalX, int goalY, int goalPosition, int goalOrientation) {
		if (currentX == goalX && currentY == goalY)
			return true;
		currentX -= insetX;
		currentY -= insetY;
		goalX -= insetX;
		goalY -= insetY;
		if (goalPosition == 0)
			if (goalOrientation == 0) {
				if (currentX == goalX - 1 && currentY == goalY)
					return true;
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0)
					return true;
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0)
					return true;
			} else if (goalOrientation == 1) {
				if (currentX == goalX && currentY == goalY + 1)
					return true;
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0)
					return true;
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0)
					return true;
			} else if (goalOrientation == 2) {
				if (currentX == goalX + 1 && currentY == goalY)
					return true;
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0)
					return true;
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0)
					return true;
			} else if (goalOrientation == 3) {
				if (currentX == goalX && currentY == goalY - 1)
					return true;
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0)
					return true;
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0)
					return true;
			}
		if (goalPosition == 2)
			if (goalOrientation == 0) {
				if (currentX == goalX - 1 && currentY == goalY)
					return true;
				if (currentX == goalX && currentY == goalY + 1)
					return true;
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0)
					return true;
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0)
					return true;
			} else if (goalOrientation == 1) {
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0)
					return true;
				if (currentX == goalX && currentY == goalY + 1)
					return true;
				if (currentX == goalX + 1 && currentY == goalY)
					return true;
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 0x1280102) == 0)
					return true;
			} else if (goalOrientation == 2) {
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280108) == 0)
					return true;
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0)
					return true;
				if (currentX == goalX + 1 && currentY == goalY)
					return true;
				if (currentX == goalX && currentY == goalY - 1)
					return true;
			} else if (goalOrientation == 3) {
				if (currentX == goalX - 1 && currentY == goalY)
					return true;
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x1280120) == 0)
					return true;
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x1280180) == 0)
					return true;
				if (currentX == goalX && currentY == goalY - 1)
					return true;
			}
		if (goalPosition == 9) {
			if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0)
				return true;
			if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 2) == 0)
				return true;
			if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 8) == 0)
				return true;
			if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0)
				return true;
		}
		return false;
	}

	public boolean reachedWallDecoration(int currentX, int currentY, int goalX, int goalY, int goalPosition, int goalOrientation) {
		currentX -= insetX;
		currentY -= insetY;
		goalX -= insetX;
		goalY -= insetY;
		if (goalPosition == 6 || goalPosition == 7) {
			if (goalPosition == 7)
				goalOrientation = goalOrientation + 2 & 3;
			if (goalOrientation == 0) {
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0)
					return true;
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 2) == 0)
					return true;
			} else if (goalOrientation == 1) {
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 8) == 0)
					return true;
				if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 2) == 0)
					return true;
			} else if (goalOrientation == 2) {
				if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 8) == 0)
					return true;
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0)
					return true;
			} else if (goalOrientation == 3) {
				if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0)
					return true;
				if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0)
					return true;
			}
		}
		if (goalPosition == 8) {
			if (currentX == goalX && currentY == goalY + 1 && (adjacency[currentX][currentY] & 0x20) == 0)
				return true;
			if (currentX == goalX && currentY == goalY - 1 && (adjacency[currentX][currentY] & 2) == 0)
				return true;
			if (currentX == goalX - 1 && currentY == goalY && (adjacency[currentX][currentY] & 8) == 0)
				return true;
			if (currentX == goalX + 1 && currentY == goalY && (adjacency[currentX][currentY] & 0x80) == 0)
				return true;
		}
		return false;
	}

	public boolean reachedFacingObject(int currentX, int currentY, int goalX, int goalY, int goalDX, int goalDY, int surroundings) {
		int goalX2 = (goalX + goalDX) - 1;
		int goalY2 = (goalY + goalDY) - 1;
		if (currentX >= goalX && currentX <= goalX2 && currentY >= goalY && currentY <= goalY2)
			return true;
		if (currentX == goalX - 1 && currentY >= goalY && currentY <= goalY2 && (adjacency[currentX - insetX][currentY - insetY] & 8) == 0
				&& (surroundings & 8) == 0)
			return true;
		if (currentX == goalX2 + 1 && currentY >= goalY && currentY <= goalY2 && (adjacency[currentX - insetX][currentY - insetY] & 0x80) == 0
				&& (surroundings & 2) == 0)
			return true;
		if (currentY == goalY - 1 && currentX >= goalX && currentX <= goalX2 && (adjacency[currentX - insetX][currentY - insetY] & 2) == 0
				&& (surroundings & 4) == 0)
			return true;
		return currentY == goalY2 + 1 && currentX >= goalX && currentX <= goalX2 && (adjacency[currentX - insetX][currentY - insetY] & 0x20) == 0
				&& (surroundings & 1) == 0;
	}


}
