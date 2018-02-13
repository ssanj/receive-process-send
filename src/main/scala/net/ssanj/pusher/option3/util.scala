package net.ssanj.pusher.option3

trait EitherOps[L, R] {
  def leftMap[L2](f: L => L2): Either[L2, R]
}
